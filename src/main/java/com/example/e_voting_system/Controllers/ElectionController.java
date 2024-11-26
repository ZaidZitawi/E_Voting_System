package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Services.ElectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ElectionDTO>> filterElections(
            @RequestParam(required = false) String faculty,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean upcoming,
            @RequestParam(required = false) Boolean active) {

        List<ElectionDTO> elections = electionService.filterElections(faculty, department, upcoming, active);
        return ResponseEntity.ok(elections);
    }



}
