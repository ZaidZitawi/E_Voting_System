package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.Candidate;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Role;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.CandidateRepository;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.RoleRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleAssignmentService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;

    public RoleAssignmentService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 ElectionRepository electionRepository,
                                 CandidateRepository candidateRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
    }


    @Transactional
    public boolean assignCandidateRoleByEmail(String email, Long electionId) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Fetch the candidate role from the database, assuming roleId of 2 corresponds to ROLE_CANDIDATE
            Optional<Role> candidateRoleOptional = roleRepository.findById(2L); // Fetch by ID
            if (candidateRoleOptional.isPresent()) {
                Role candidateRole = candidateRoleOptional.get();

                // Update user role to ROLE_CANDIDATE
                user.setRole(candidateRole);
                userRepository.save(user); // Save user with the updated role

                // Fetch the Election by ID
                Optional<Election> electionOptional = electionRepository.findById(electionId);
                if (electionOptional.isPresent()) {
                    Election election = electionOptional.get();

                    // Create and save new Candidate entry
                    Candidate candidate = new Candidate();
                    candidate.setUser(user);
                    candidate.setElection(election);

                    candidateRepository.save(candidate); // Save candidate entry

                    return true;
                } else {
                    System.out.println("Election not found in the database.");
                    return false;
                }
            } else {
                System.out.println("Candidate role not found in the database.");
                return false;
            }
        }
        return false; // User not found
    }

}
