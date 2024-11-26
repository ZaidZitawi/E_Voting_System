package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.CommentDTO;
import com.example.e_voting_system.Model.DTO.CommentResponseDTO;
import com.example.e_voting_system.Services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // POST /comments - Add a comment to a post
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') || hasRole('CANDIDATE')")
    public ResponseEntity<CommentResponseDTO> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        CommentResponseDTO createdComment = commentService.addComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }


    // GET /comments/post/{postId} - Get all comments for a post
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsForPost(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }





    /*You don’t pass the Principal directly from the frontend.
     Instead, the authentication token (e.g., JWT) is sent with each HTTP request,
      and Spring Security extracts the Principal from the token on the server side.*/

    // PUT /comments/{commentId} - Edit a comment
    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('USER') || hasRole('CANDIDATE')")
    public ResponseEntity<CommentResponseDTO> editComment(@PathVariable Long commentId,
                                                          @Valid @RequestBody CommentDTO commentDTO,
                                                          Principal principal) {
        CommentResponseDTO updatedComment = commentService.editComment(commentId, commentDTO, principal.getName());
        return ResponseEntity.ok(updatedComment);
    }

    // DELETE /comments/{commentId} - Delete a comment
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER') || hasRole('CANDIDATE')")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, Principal principal) {
        commentService.deleteComment(commentId, principal.getName());
        return ResponseEntity.ok("Comment deleted successfully.");
    }
}
