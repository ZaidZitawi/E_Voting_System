package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.LikeResponseDTO;
import com.example.e_voting_system.Services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {


    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping("/posts/{postId}/likers")
    public ResponseEntity<List<LikeResponseDTO>> getLikersForPost(@PathVariable Long postId) {
        List<LikeResponseDTO> likers = likeService.getLikersForPost(postId);
        return ResponseEntity.ok(likers);
    }


    @PostMapping("/posts/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        likeService.likePost(postId, userId);
        return ResponseEntity.ok("Post liked successfully.");
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        likeService.unlikePost(postId, userId);
        return ResponseEntity.ok("Post unliked successfully.");
    }

}
