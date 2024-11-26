package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Mapper.CommentMapper;
import com.example.e_voting_system.Model.DTO.CommentDTO;
import com.example.e_voting_system.Model.DTO.CommentResponseDTO;
import com.example.e_voting_system.Model.Entity.Comment;
import com.example.e_voting_system.Model.Entity.Post;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.CommentRepository;
import com.example.e_voting_system.Repositories.PostRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
                          UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponseDTO addComment(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = commentMapper.toComment(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(ZonedDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponseDTO(savedComment);
    }



    public List<CommentResponseDTO> getCommentsForPost(Long postId) {
        List<Comment> comments = commentRepository.findByPost_PostId(postId);
        return comments.stream()
                .map(commentMapper::toCommentResponseDTO)
                .collect(Collectors.toList());
    }


    public CommentResponseDTO editComment(Long commentId, CommentDTO commentDTO, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getUser().getEmail().equals(username)) {
            throw new SecurityException("You can only edit your own comments.");
        }

        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(ZonedDateTime.now());
        commentRepository.save(comment);

        return commentMapper.toCommentResponseDTO(comment);
    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getUser().getEmail().equals(username)) {
            throw new SecurityException("You can only delete your own comments.");
        }

        commentRepository.delete(comment);
    }
}
