package com.example.e_voting_system.Services;


import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Mapper.ElectionMapper;
import com.example.e_voting_system.Repositories.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    public ElectionService(ElectionRepository electionRepository, ElectionMapper electionMapper) {
        this.electionRepository = electionRepository;
        this.electionMapper = electionMapper;
    }

    // Get all elections
    public List<ElectionDTO> getAllElections() {
        return electionRepository.findAll()
                .stream()
                .map(electionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get election by ID
    public Optional<ElectionDTO> getElectionById(Long id) {
        return electionRepository.findById(id).map(electionMapper::toDTO);
    }

    // Search elections by title
    public List<ElectionDTO> searchElections(String searchTerm) {
        return electionRepository.findByTitleContainingIgnoreCase(searchTerm)
                .stream()
                .map(electionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Create new election (Admin only)
    public ElectionDTO createElection(ElectionDTO electionDTO) {
        Election election = electionMapper.toEntity(electionDTO);
        election.setIsActive(true); // Set the default state if needed
        Election savedElection = electionRepository.save(election);
        return electionMapper.toDTO(savedElection);
    }
}
