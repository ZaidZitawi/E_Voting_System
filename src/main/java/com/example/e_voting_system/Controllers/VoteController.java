package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Services.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * Endpoint to cast a vote.
     * We'll derive the voter from JWT token, so no voterId param needed.
     */
    @PostMapping("/cast")
    public ResponseEntity<String> castVote(
            @RequestParam Long electionId,
            @RequestParam Long partyId,
            @RequestParam(required = false, defaultValue = "anonymous") String characterName,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            // 1) Derive email (or ID) from userDetails
            String email = userDetails.getUsername();
            // Or if you have a custom principal storing user ID, you can get it directly

            // 2) Call the service with the email or the entire user if you prefer
            String transactionHash = voteService.castVote(email, electionId, partyId, characterName);
            return ResponseEntity.ok("Vote cast successfully. Transaction Hash: " + transactionHash);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error casting vote: " + e.getMessage());
        }
    }
}
