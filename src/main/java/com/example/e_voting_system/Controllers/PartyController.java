package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.PartyDTO;
import com.example.e_voting_system.Services.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }


      //Returns a list of parties belonging to a specific election.
      //For example: GET /parties/election/27
    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<PartyDTO>> getPartiesForElection(@PathVariable Long electionId) {
        List<PartyDTO> parties = partyService.getPartiesForElection(electionId);
        return ResponseEntity.ok(parties);
    }

    // If you want a direct GET by partyId:
    // e.g. GET /parties/{partyId}
    @GetMapping("/{partyId}")
    public ResponseEntity<PartyDTO> getParty(@PathVariable Long partyId) {
        PartyDTO dto = partyService.getPartyById(partyId);
        if (dto != null) return ResponseEntity.ok(dto);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PartyDTO> getPartyByUserId(@PathVariable Long userId) {
        PartyDTO partyDTO = partyService.getPartyByUserId(userId);
        return ResponseEntity.ok(partyDTO);
    }


    @DeleteMapping("/election/{electionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePartiesForElection(@PathVariable Long electionId) {
        partyService.deletePartiesForElection(electionId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{partyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePartyById(@PathVariable Long partyId) {
        partyService.deletePartyById(partyId);
        return ResponseEntity.ok("Party deleted and roles updated successfully.");
    }


}
