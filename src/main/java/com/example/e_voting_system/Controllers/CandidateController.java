package com.example.e_voting_system.Controllers;


import com.example.e_voting_system.Model.DTO.CandidateDTO;
import com.example.e_voting_system.Model.DTO.CandidateSummaryDTO;
import com.example.e_voting_system.Services.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {


    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateDTO> getCandidateDetails(@PathVariable Long candidateId) {
        CandidateDTO candidateDetails = candidateService.getCandidateDetails(candidateId);
        return ResponseEntity.ok(candidateDetails);
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<CandidateSummaryDTO>> getCandidatesByElection(@PathVariable Long electionId) {
        List<CandidateSummaryDTO> candidates = candidateService.getCandidatesByElection(electionId);
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<CandidateSummaryDTO>> getCandidatesByParty(@PathVariable Long partyId) {
        List<CandidateSummaryDTO> candidates = candidateService.getCandidatesByParty(partyId);
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CandidateDTO> getCandidateByUserId(@PathVariable Long userId) {
        CandidateDTO candidateDTO = candidateService.getCandidateByUserId(userId);
        return ResponseEntity.ok(candidateDTO);
    }

    @DeleteMapping("/{candidateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCandidateById(@PathVariable Long candidateId) {
        candidateService.deleteCandidateById(candidateId);
        return ResponseEntity.ok("Candidate deleted and user role updated successfully.");
    }

}
