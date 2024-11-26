package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
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

    public AdminController(ElectionService electionService, RoleAssignmentService roleAssignmentService) {
        this.electionService = electionService;
        this.roleAssignmentService = roleAssignmentService;
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
}
