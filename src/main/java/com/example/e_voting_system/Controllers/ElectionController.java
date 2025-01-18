package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Model.DTO.ElectionDTO2;
import com.example.e_voting_system.Services.ElectionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/elections")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    // GET /elections - Get all elections
    @GetMapping
    public ResponseEntity<List<ElectionDTO>> getAllElections() {
        List<ElectionDTO> elections = electionService.getAllElections();
        return ResponseEntity.ok(elections);
    }

    // GET /elections/{id} - Get a specific election by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getElectionById(@PathVariable Long id) {
        Optional<ElectionDTO> election = electionService.getElectionById(id);
        return election.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /elections/search?query={searchTerm} - Search for elections
    @GetMapping("/search")
    public ResponseEntity<List<ElectionDTO>> searchElections(@RequestParam("query") String searchTerm) {
        List<ElectionDTO> elections = electionService.searchElections(searchTerm);
        return ResponseEntity.ok(elections);
    }



    @GetMapping("/filter")
    public ResponseEntity<Page<ElectionDTO2>> filterElections(
            @RequestParam(required = false) Long faculty, // Changed to Long (facultyId)
            @RequestParam(required = false) Long department, // Changed to Long (departmentId)
            @RequestParam(required = false) Boolean upcoming,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Integer type, // Changed to Integer (typeId)
            Pageable pageable) { // Added Pageable

        Page<ElectionDTO2> elections = electionService.filterElections(faculty, department, upcoming, active, type, pageable);
        return ResponseEntity.ok(elections);
    }



    @GetMapping("/featured")
    public ResponseEntity<List<ElectionDTO>> getFeaturedElections(Authentication authentication) {
        List<ElectionDTO> featuredElections = electionService.getFeaturedElections(authentication);
        return ResponseEntity.ok(featuredElections);
    }



}
