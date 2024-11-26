package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.CommentResponseDTO;
import com.example.e_voting_system.Model.DTO.PostDTO;
import com.example.e_voting_system.Model.DTO.PostResponseDTO;
import com.example.e_voting_system.Services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Publish a new post (Candidate only)
    @PostMapping("candidate/createPost")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<PostResponseDTO> publishPost(@Valid @RequestBody PostDTO postDTO) {
        PostResponseDTO postResponse = postService.createPost(postDTO);
        return ResponseEntity.ok(postResponse);
    }

    // Fetch all posts for an election
    @GetMapping("/election/{electionId}/posts")
    public ResponseEntity<List<PostResponseDTO>> getAllPostsForElection(
            @PathVariable Long electionId) {
        List<PostResponseDTO> posts = postService.getElectionPosts(electionId);
        return ResponseEntity.ok(posts);
    }

    // Edit a post (Candidate only, must be the owner of the post)
    @PutMapping("/{postId}/edit")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<PostResponseDTO> editPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostDTO postDTO,
            Principal principal) {
        PostResponseDTO updatedPost = postService.editPost(postId, postDTO, principal.getName());
        return ResponseEntity.ok(updatedPost);
    }

    // Delete a post (Candidate only, must be the owner of the post)
    @DeleteMapping("/{postId}/delete")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, Principal principal) {
        postService.deletePost(postId, principal.getName());
        return ResponseEntity.ok("Post deleted successfully.");
    }
}














//    // Get a single post by ID
//    @GetMapping("/{postId}")
//    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
//        PostResponseDTO post = postService.getPostById(postId);
//        return ResponseEntity.ok(post);
//    }
//
//    // Edit a post
//    @PutMapping("/{postId}")
//    @PreAuthorize("hasRole('CANDIDATE')")
//    public ResponseEntity<PostResponseDTO> editPost(@PathVariable Long postId,
//                                                    @Valid @RequestBody PostDTO postDTO,
//                                                    Principal principal) {
//        PostResponseDTO updatedPost = postService.editPost(postId, postDTO, principal.getName());
//        return ResponseEntity.ok(updatedPost);
//    }
//
//    // Delete a post
//    @DeleteMapping("/{postId}")
//    @PreAuthorize("hasRole('CANDIDATE')")
//    public ResponseEntity<String> deletePost(@PathVariable Long postId, Principal principal) {
//        postService.deletePost(postId, principal.getName());
//        return ResponseEntity.ok("Post deleted successfully.");
//    }
//
//    // Like a post
//    @PostMapping("/{postId}/like")
//    @PreAuthorize("hasRole('USER') || hasRole('CANDIDATE')")
//    public ResponseEntity<String> likePost(@PathVariable Long postId, Principal principal) {
//        postService.likePost(postId, principal.getName());
//        return ResponseEntity.ok("Post liked successfully.");
//    }
//
//    // Unlike a post
//    @DeleteMapping("/{postId}/like")
//    @PreAuthorize("hasRole('USER') || hasRole('CANDIDATE')")
//    public ResponseEntity<String> unlikePost(@PathVariable Long postId, Principal principal) {
//        postService.unlikePost(postId, principal.getName());
//        return ResponseEntity.ok("Post unliked successfully.");
//    }
