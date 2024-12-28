package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.PartyRepository;
import com.example.e_voting_system.Services.BlockchainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {

    private final BlockchainService blockchainService;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;

    public BlockchainController(BlockchainService blockchainService,
                                ElectionRepository electionRepository,
                                PartyRepository partyRepository) {
        this.blockchainService = blockchainService;
        this.electionRepository = electionRepository;
        this.partyRepository = partyRepository;
    }

    @PostMapping("/elections/{electionId}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> publishElectionToBlockchain(@PathVariable Long electionId) {
        try {
            // Fetch the election and associated parties
            Election election = electionRepository.findById(electionId)
                    .orElseThrow(() -> new IllegalArgumentException("Election not found"));
            List<Party> parties = partyRepository.findByElection_ElectionId(electionId);

            // Call the blockchain service
            blockchainService.publishElectionToBlockchain(election, parties);

            return ResponseEntity.ok("Election and parties successfully published to the blockchain.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}

