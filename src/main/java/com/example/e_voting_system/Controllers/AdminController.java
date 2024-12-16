package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Services.CandidateService;
import com.example.e_voting_system.Services.ElectionService;
import com.example.e_voting_system.Services.RoleAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ElectionService electionService;
    private final RoleAssignmentService roleAssignmentService;

    private final CandidateService candidateService;


    public AdminController(ElectionService electionService,
                           RoleAssignmentService roleAssignmentService,
                           CandidateService candidateService) {
        this.electionService = electionService;
        this.roleAssignmentService = roleAssignmentService;
        this.candidateService = candidateService;
    }

    // POST /elections - Create a new election (Admin only)
    @PostMapping("elections/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ElectionDTO> createElection(@Valid @RequestBody ElectionDTO electionDTO) {
        ElectionDTO createdElection = electionService.createElection(electionDTO);
        return ResponseEntity.ok(createdElection);
    }

    // POST /candidates/assign - Assign a candidate role to a user by university email (Admin only)
    @PostMapping("candidates/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignCandidateRole(@RequestParam String email, @RequestParam Long electionId) {
        boolean success = roleAssignmentService.assignCandidateRoleByEmail(email, electionId);
        if (success) {
            return ResponseEntity.ok("User assigned as candidate successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to assign user as candidate. User not found or other error.");
        }
    }

    @PutMapping("elections/{electionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ElectionDTO> updateElection(
            @PathVariable Long electionId,
            @Valid @RequestBody ElectionDTO electionDTO) {
        ElectionDTO updatedElection = electionService.updateElection(electionId, electionDTO);
        return ResponseEntity.ok(updatedElection);
    }

    @DeleteMapping("elections/{electionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteElection(@PathVariable Long electionId) {
        electionService.deleteElection(electionId);
        return ResponseEntity.ok("Election deleted successfully.");
    }


    //delete candidate and update his role to ROLE_USER
    @DeleteMapping("/candidates/{candidateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long candidateId) {
        candidateService.deleteCandidate(candidateId);
        return ResponseEntity.ok("Candidate deleted and user role updated successfully.");
    }





}
