package com.example.e_voting_system.Services;

import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Model.DTO.PartyDTO;
import com.example.e_voting_system.Model.Entity.*;
import com.example.e_voting_system.Model.Mapper.PartyMapper;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.PartyRepository;
import com.example.e_voting_system.Repositories.RoleRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final ElectionRepository electionRepository;
    private final PartyMapper partyMapper;
    private final RoleRepository roleRepository;

    public PartyService(PartyRepository partyRepository, UserRepository userRepository,
                        ElectionRepository electionRepository, PartyMapper partyMapper,
                        RoleRepository roleRepository) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.electionRepository = electionRepository;
        this.partyMapper = partyMapper;
        this.roleRepository=roleRepository;
    }

    public PartyDTO createParty(PartyDTO partyDTO) {
        // Validate campaign manager
        Optional<User> campaignManagerOpt = userRepository.findById(partyDTO.getCampaignManagerId());
        if (campaignManagerOpt.isEmpty()) {
            throw new IllegalArgumentException("Campaign manager not found with ID: " + partyDTO.getCampaignManagerId());
        }

        // Validate election
        Optional<Election> electionOpt = electionRepository.findById(partyDTO.getElectionId());
        if (electionOpt.isEmpty()) {
            throw new IllegalArgumentException("Election not found with ID: " + partyDTO.getElectionId());
        }

        // Map DTO to Entity
        Party party = partyMapper.toEntity(partyDTO);
        party.setCampaignManager(campaignManagerOpt.get());
        party.setElection(electionOpt.get());

        // Save to database
        Party savedParty = partyRepository.save(party);

        // Map Entity back to DTO and return
        return partyMapper.toDTO(savedParty);
    }

    public List<PartyDTO> getPartiesForElection(Long electionId) {
        // Query the parties by electionId
        List<Party> parties = partyRepository.findByElection_ElectionId(electionId);
        // Convert to DTO
        return parties.stream()
                .map(partyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PartyDTO getPartyById(Long partyId) {
        Optional<Party> partyOpt = partyRepository.findById(partyId);
        return partyOpt.map(partyMapper::toDTO).orElse(null);
    }

    public PartyDTO getPartyByUserId(Long userId) {
        Party party = partyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found for user ID: " + userId));
        return partyMapper.toDTO(party);
    }

    @Transactional
    public void deletePartiesForElection(Long electionId) {
        List<Party> parties = partyRepository.findByElection_ElectionId(electionId);
        if (parties.isEmpty()) {
            throw new ResourceNotFoundException("No parties found for election ID: " + electionId);
        }

        // Fetch the user role (assuming roleId of 1 corresponds to ROLE_USER)
        Optional<Role> userRoleOptional = roleRepository.findById(1L);
        if (userRoleOptional.isEmpty()) {
            throw new IllegalArgumentException("User role not found in the database.");
        }
        Role userRole = userRoleOptional.get();

        // Reassign campaign manager's role to ROLE_USER
        for (Party party : parties) {
            User campaignManager = party.getCampaignManager();
            campaignManager.setRole(userRole);
            userRepository.save(campaignManager);
        }

        // Delete all parties
        partyRepository.deleteAll(parties);
    }

    @jakarta.transaction.Transactional
    public void deletePartyById(Long partyId) {
        Optional<Party> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isEmpty()) {
            throw new ResourceNotFoundException("Party not found with ID: " + partyId);
        }

        Party party = partyOptional.get();

        // Fetch the user role (assuming roleId of 1 corresponds to ROLE_USER)
        Optional<Role> userRoleOptional = roleRepository.findById(1L);
        if (userRoleOptional.isEmpty()) {
            throw new IllegalArgumentException("User role not found in the database.");
        }
        Role userRole = userRoleOptional.get();

        // Reassign campaign manager's role to ROLE_USER
        User campaignManager = party.getCampaignManager();
        campaignManager.setRole(userRole);
        userRepository.save(campaignManager);

        // Reassign each candidate's role to ROLE_USER
        List<Candidate> candidates = party.getCandidates();
        for (Candidate candidate : candidates) {
            User candidateUser = candidate.getUser();
            candidateUser.setRole(userRole);
            userRepository.save(candidateUser);
        }

        // Delete the party
        partyRepository.delete(party);
    }
}
