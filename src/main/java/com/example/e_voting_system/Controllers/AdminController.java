package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Model.DTO.PartyDTO;
import com.example.e_voting_system.Model.DTO.UserDTO;
import com.example.e_voting_system.Services.CandidateService;
import com.example.e_voting_system.Services.ElectionService;
import com.example.e_voting_system.Services.PartyService;
import com.example.e_voting_system.Services.RoleAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ElectionService electionService;
    private final RoleAssignmentService roleAssignmentService;
    private final CandidateService candidateService;
    private final PartyService partyService;


    public AdminController(ElectionService electionService,
                           RoleAssignmentService roleAssignmentService,
                           CandidateService candidateService,
                           PartyService partyService) {
        this.electionService = electionService;
        this.roleAssignmentService = roleAssignmentService;
        this.candidateService = candidateService;
        this.partyService=partyService;
    }


    //Step 1: Create a new election
    // POST /elections - Create a new election (Admin only)
    @PostMapping("elections/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ElectionDTO> createElection(@Valid @RequestBody ElectionDTO electionDTO) {
        ElectionDTO createdElection = electionService.createElection(electionDTO);
        return ResponseEntity.ok(createdElection);
    }


    // Step 2: Create the party and assign the PARTY_MANAGER role to the specified user
    @PostMapping("/parties/create-with-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PartyDTO> createPartyWithManager(@Valid @RequestBody PartyDTO partyDTO) {

        PartyDTO createdParty = partyService.createParty(partyDTO);


        roleAssignmentService.assignPartyManagerRole(partyDTO.getCampaignManagerId());


        return ResponseEntity.ok(createdParty);
    }


    //Step 3: Assign a candidate role to a party
    @PostMapping("parties/{partyId}/assign-candidates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignCandidatesToParty(
            @PathVariable Long partyId,
            @RequestBody List<Long> usersIDs) {
        roleAssignmentService.assignCandidatesToParty(partyId, usersIDs);
        return ResponseEntity.ok("Candidates assigned to party successfully.");
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


//    //fetch all parties for an election
//    @GetMapping("elections/{electionId}/parties")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<PartyDTO>> getPartiesForElection(@PathVariable Long electionId) {
//        List<PartyDTO> parties = partyService.getPartiesForElection(electionId);
//        return ResponseEntity.ok(parties);
//    }
//
//
//    //fetch all candidates for a party
//    @GetMapping("parties/{partyId}/candidates")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> getCandidatesForParty(@PathVariable Long partyId) {
//        List<UserDTO> candidates = candidateService.getCandidatesForParty(partyId);
//        return ResponseEntity.ok(candidates);
//    }



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
