package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.EligibilityResponseDTO;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import com.example.e_voting_system.Services.EligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/eligibility")
public class EligibilityController {

    private final EligibilityService eligibilityService;
    private final ElectionRepository electionRepository;
    private final UserRepository userRepository;

    @Autowired
    public EligibilityController(
            EligibilityService eligibilityService,
            ElectionRepository electionRepository,
            UserRepository userRepository
    ) {
        this.eligibilityService = eligibilityService;
        this.electionRepository = electionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/elections/{electionId}/check")
    public ResponseEntity<?> checkEligibility(
            @PathVariable Long electionId,
            Authentication authentication
    ) {
        // 1) Extract username (email) from Authentication
        String username = authentication.getName();

        // 2) Fetch the user (Optional<User>)
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No user found for the provided email: " + username);
        }
        User currentUser = optionalUser.get();

        // 3) Fetch the election
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found"));

        // 4) Check eligibility (service method still takes a User, not Optional<User>)
        boolean isEligible = eligibilityService.isUserEligibleForElection(currentUser, election);

        // 5) Return a small JSON body => { "eligible": true/false }
        return ResponseEntity.ok().body(new EligibilityResponseDTO(isEligible));
    }
}
