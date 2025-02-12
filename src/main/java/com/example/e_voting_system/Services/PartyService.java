package com.example.e_voting_system.Services;

import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Model.DTO.PartyDTO;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Model.Mapper.PartyMapper;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.PartyRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final ElectionRepository electionRepository;
    private final PartyMapper partyMapper;

    public PartyService(PartyRepository partyRepository, UserRepository userRepository,
                        ElectionRepository electionRepository, PartyMapper partyMapper) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.electionRepository = electionRepository;
        this.partyMapper = partyMapper;
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
}
