package com.example.e_voting_system.Services;


import com.example.e_voting_system.Model.DTO.LikeResponseDTO;
import com.example.e_voting_system.Model.Entity.Like;
import com.example.e_voting_system.Model.Mapper.LikeMapper;
import com.example.e_voting_system.Repositories.CommentRepository;
import com.example.e_voting_system.Repositories.LikeRepository;
import com.example.e_voting_system.Repositories.PostRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {


    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository,
                       LikeMapper likeMapper,
                       PostRepository postRepository,
                       UserRepository userRepository){
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<LikeResponseDTO> getLikersForPost(Long postId) {
        List<Like> likes = likeRepository.findByPost_PostId(postId);
        return likes.stream()
                .map(likeMapper::toLikeResponseDTO)
                .collect(Collectors.toList());
    }

    public void likePost(Long postId, Long userId) {
        if (likeRepository.existsByPost_PostIdAndUser_UserId(postId, userId)) {
            throw new IllegalStateException("User already liked this post.");
        }
        Like like = new Like();
        like.setPost(postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found.")));
        like.setUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found.")));
        likeRepository.save(like);
    }

    public void unlikePost(Long postId, Long userId) {
        Like like = likeRepository.findByPost_PostIdAndUser_UserId(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found."));
        likeRepository.delete(like);
    }

}
