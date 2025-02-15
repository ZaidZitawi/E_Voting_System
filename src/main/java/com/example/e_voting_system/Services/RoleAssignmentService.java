package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.*;
import com.example.e_voting_system.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleAssignmentService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final PartyRepository partyRepository;

    public RoleAssignmentService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 ElectionRepository electionRepository,
                                 CandidateRepository candidateRepository,
                                 PartyRepository partyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
        this.partyRepository = partyRepository;
    }


    //method for Party Manager role assignment
    @Transactional
    public void assignPartyManagerRole(Long userId) {
        // Fetch the user by ID
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        User user = userOptional.get();

        // Fetch the role for PARTY_MANAGER (id=4)
        Optional<Role> partyManagerRoleOptional = roleRepository.findById(4L);
        if (partyManagerRoleOptional.isEmpty()) {
            throw new IllegalArgumentException("Party Manager role not found in the database.");
        }

        Role partyManagerRole = partyManagerRoleOptional.get();

        // Assign the role to the user
        user.setRole(partyManagerRole);

        // Save the updated user entity
        userRepository.save(user);
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


    @Transactional
    public void assignCandidatesToParty(Long partyId, List<Long> usersIDs) {
        // Fetch the party by ID
        Optional<Party> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isEmpty()) {
            throw new IllegalArgumentException("Party not found with ID: " + partyId);
        }
        Party party = partyOptional.get();

        // Fetch the election associated with the party
        Election election = party.getElection();
        if (election == null) {
            throw new IllegalArgumentException("No election associated with the party ID: " + partyId);
        }

        // Fetch the candidate role
        Optional<Role> candidateRoleOptional = roleRepository.findById(2L); // ROLE_CANDIDATE ID = 2
        if (candidateRoleOptional.isEmpty()) {
            throw new IllegalArgumentException("Candidate role not found in the database.");
        }
        Role candidateRole = candidateRoleOptional.get();

        for (Long userId : usersIDs) {
            // Fetch each user by ID
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            User user = userOptional.get();

            // Assign the role of "ROLE_CANDIDATE" to the user
            user.setRole(candidateRole);
            userRepository.save(user);

            // Create a new Candidate entity
            Candidate candidate = new Candidate();
            candidate.setUser(user);
            candidate.setParty(party);
            candidate.setElection(election);

            // Save the candidate to the database
            candidateRepository.save(candidate);
        }
    }

}
