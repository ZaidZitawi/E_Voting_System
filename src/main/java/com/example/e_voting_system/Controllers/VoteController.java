package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Services.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/cast")
    public ResponseEntity<String> castVote(
            @RequestParam Long voterId,
            @RequestParam Long electionId,
            @RequestParam Long partyId) {
        try {
            String transactionHash = voteService.castVote(voterId, electionId, partyId);
            return ResponseEntity.ok("Vote cast successfully. Transaction Hash: " + transactionHash);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error casting vote: " + e.getMessage());
        }
    }
}
