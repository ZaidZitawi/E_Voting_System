package com.example.e_voting_system.Services;


import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Exceptions.UnauthorizedException;
import com.example.e_voting_system.Model.DTO.CommentResponseDTO;
import com.example.e_voting_system.Model.DTO.PostResponseDTO;
import com.example.e_voting_system.Model.Entity.*;
import com.example.e_voting_system.Model.Mapper.CommentMapper;
import com.example.e_voting_system.Model.Mapper.PostMapper;
import com.example.e_voting_system.Repositories.*;
import com.example.e_voting_system.Model.DTO.PostDTO;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final EligibilityService eligibilityService;
    private final NotificationService notificationService;
    private final FileUploadService fileUploadService;
    private final ElectionRepository electionRepository;
    public PostService(PostRepository postRepository,
                       PostMapper postMapper,
                       CommentRepository commentRepository,
                       LikeRepository likeRepository,
                       EligibilityService eligibilityService,
                       NotificationService notificationService,
                       FileUploadService fileUploadService,
                       ElectionRepository electionRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.commentRepository = commentRepository;
        this.likeRepository=likeRepository;
        this.eligibilityService = eligibilityService;
        this.notificationService = notificationService;
        this.fileUploadService = fileUploadService;
        this.electionRepository = electionRepository;
    }

    @Transactional
    public PostResponseDTO createPost(PostDTO postDTO) {
        // Convert PostDTO to Post entity
        Post post = postMapper.toEntity(postDTO);
        post.setCreatedAt(ZonedDateTime.now());

        // Handle media file upload
        if (postDTO.getMedia() != null && !postDTO.getMedia().isEmpty()) {
            try {
                String mediaPath = fileUploadService.uploadFile(postDTO.getMedia(), "post_media");
                post.setMediaUrl(mediaPath); // Save the file path to the database
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload media file", e);
            }
        }

        // Save Post entity
        Post savedPost = postRepository.save(post);

        // Instead of using savedPost.getCandidate().getElection() which is partially mapped,
        // fetch the full Election from the database:
        Election fullElection = electionRepository.findById(postDTO.getElectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Election not found"));

        // Now use fullElection for eligibility check
        List<User> eligibleUsers = eligibilityService.getEligibleUsers(fullElection);

        // Build notification message based on the candidate's full details.
        String candidateName = savedPost.getCandidate() != null && savedPost.getCandidate().getUser() != null
                ? savedPost.getCandidate().getUser().getName()
                : "A candidate";
        String message = candidateName +
                " created a new post in the election \"" +
                fullElection.getTitle() + "\".";
        notificationService.notifyUsers(eligibleUsers, message);

        PostResponseDTO postResponse = postMapper.toResponseDTO(savedPost);
        postResponse.setCommentCount(0);
        postResponse.setLikeCount(0);
        postResponse.setLikedByCurrentUser(false);
        return postResponse;
    }




    public List<PostResponseDTO> getElectionPosts(Long electionId, Long userId) {
        List<Post> posts = postRepository.findByElection_ElectionId(electionId);
        return posts.stream()
                .map(post -> {
                    PostResponseDTO responseDTO = postMapper.toResponseDTO(post);
                    // Fetch counts for comments and likes
                    int commentCount = commentRepository.countByPost_PostId(post.getPostId());
                    int likeCount = likeRepository.countByPost_PostId(post.getPostId());
                    boolean likedByCurrentUser = likeRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), userId);
                    responseDTO.setCommentCount(commentCount);
                    responseDTO.setLikeCount(likeCount);
                    responseDTO.setLikedByCurrentUser(likedByCurrentUser);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }


    public PostResponseDTO editPost(Long postId, PostDTO postDTO, String candidateEmail) throws IOException {
        // Find the post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Check if the candidate owns the post
        if (!post.getCandidate().getUser().getEmail().equals(candidateEmail)) {
            throw new UnauthorizedException("You are not authorized to edit this post.");
        }

        // Update the post fields

        post.setContent(postDTO.getContent());
        post.setMediaUrl(fileUploadService.uploadFile(postDTO.getMedia(), "post_media"));

        // Save the updated post
        Post updatedPost = postRepository.save(post);

        // Return the response DTO
        return postMapper.toResponseDTO(updatedPost);
    }


    public void deletePost(Long postId, String candidateEmail) {
        // Find the post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Check if the candidate owns the post
        if (!post.getCandidate().getUser().getEmail().equals(candidateEmail)) {
            throw new SecurityException("You are not authorized to delete this post.");
        }

        // Delete the post
        postRepository.delete(post);
    }


    public Page<PostResponseDTO> getAllPosts(int page, int size, Long userId, Long faculty, String dateRange, String sortBy, String keyword) {
        Specification<Post> spec = Specification.where(null);

        // Filter by Faculty (using faculty id)
        if (faculty != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Post, Election> electionJoin = root.join("election", JoinType.INNER);
                Join<Election, Faculty> facultyJoin = electionJoin.join("faculty", JoinType.INNER);
                return cb.equal(facultyJoin.get("facultyId"), faculty);
            });
        }

        // Filter by Date Range
        if (dateRange != null && !dateRange.trim().isEmpty()) {
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime fromDate = now;
            switch (dateRange) {
                case "24h":
                    fromDate = now.minusHours(24);
                    break;
                case "week":
                    fromDate = now.minusDays(7);
                    break;
                case "month":
                    fromDate = now.minusDays(30);
                    break;
                default:
                    break;
            }
            ZonedDateTime finalFromDate = fromDate;
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), finalFromDate));
        }

        // Filter by keyword in post content
        if (keyword != null && !keyword.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%")
            );
        }

        // Default sorting: most recent (by createdAt descending)
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // If a sort option is provided that uses custom queries (by likes or comments)
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            if (sortBy.equalsIgnoreCase("likes")) {
                return postRepository.findAllOrderByLikes(pageable).map(post -> {
                    PostResponseDTO dto = postMapper.toResponseDTO(post);
                    int likeCount = likeRepository.countByPost_PostId(post.getPostId());
                    int commentCount = commentRepository.countByPost_PostId(post.getPostId());
                    boolean likedByCurrentUser = likeRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), userId);
                    dto.setLikeCount(likeCount);
                    dto.setCommentCount(commentCount);
                    dto.setLikedByCurrentUser(likedByCurrentUser);
                    return dto;
                });
            } else if (sortBy.equalsIgnoreCase("comments")) {
                return postRepository.findAllOrderByComments(pageable).map(post -> {
                    PostResponseDTO dto = postMapper.toResponseDTO(post);
                    int likeCount = likeRepository.countByPost_PostId(post.getPostId());
                    int commentCount = commentRepository.countByPost_PostId(post.getPostId());
                    boolean likedByCurrentUser = likeRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), userId);
                    dto.setLikeCount(likeCount);
                    dto.setCommentCount(commentCount);
                    dto.setLikedByCurrentUser(likedByCurrentUser);
                    return dto;
                });
            }
        }

        // Use specifications if no custom sort is required
        Page<Post> postPage = postRepository.findAll(spec, pageable);
        return postPage.map(post -> {
            PostResponseDTO dto = postMapper.toResponseDTO(post);
            int likeCount = likeRepository.countByPost_PostId(post.getPostId());
            int commentCount = commentRepository.countByPost_PostId(post.getPostId());
            boolean likedByCurrentUser = likeRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), userId);
            dto.setLikeCount(likeCount);
            dto.setCommentCount(commentCount);
            dto.setLikedByCurrentUser(likedByCurrentUser);
            return dto;
        });
    }


    public Page<PostResponseDTO> getAllPosts(int page, int size, Long userId) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(post -> {
            PostResponseDTO dto = postMapper.toResponseDTO(post);
            // Use the repositories to determine the like count and comment count.
            int likeCount = likeRepository.countByPost_PostId(post.getPostId());
            int commentCount = commentRepository.countByPost_PostId(post.getPostId());
            // Check if the current user liked this post.
            boolean likedByCurrentUser = likeRepository.existsByPost_PostIdAndUser_UserId(post.getPostId(), userId);

            dto.setLikeCount(likeCount);
            dto.setCommentCount(commentCount);
            dto.setLikedByCurrentUser(likedByCurrentUser);

            return dto;
        });
    }
}
