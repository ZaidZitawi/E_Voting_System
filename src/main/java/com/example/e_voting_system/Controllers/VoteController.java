package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.VoteDTO;
import com.example.e_voting_system.Services.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    // Endpoint to cast a vote.
    //We'll derive the voter from JWT token, so no voterId param needed.

    @PostMapping("/cast")
    public ResponseEntity<String> castVote(
            @RequestParam Long electionId,
            @RequestParam Long partyId,
            @RequestParam(required = false, defaultValue = "anonymous") String characterName,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            // 1) Derive email  from userDetails
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



    // Example: GET /votes/election/27/myVote

    @GetMapping("/election/{electionId}/myVote")
    public ResponseEntity<?> getMyVoteForElection(
            @PathVariable Long electionId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            String email = userDetails.getUsername();
            VoteDTO myVote = voteService.getMyVote(email, electionId);
            if (myVote != null) {
                return ResponseEntity.ok(myVote);
            } else {
                // response as object for a better handling brother!!!
                Map<String, Object> response = new HashMap<>();
                response.put("status", "no_vote");
                response.put("message", "No vote found for the current user.");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error fetching vote: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
