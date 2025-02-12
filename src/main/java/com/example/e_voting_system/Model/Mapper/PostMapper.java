package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.PostDTO;
import com.example.e_voting_system.Model.DTO.PostResponseDTO;
import com.example.e_voting_system.Model.DTO.MinimalPartyDTO;
import com.example.e_voting_system.Model.DTO.MinimalCandidateDTO;
import com.example.e_voting_system.Model.Entity.Candidate;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Model.Entity.Post;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // Map Post to PostDTO (if needed)
    @Mapping(source = "candidate.candidateId", target = "candidate")
    @Mapping(source = "election.electionId", target = "electionId")
    @Mapping(source = "party.partyId", target = "party")
    PostDTO toDTO(Post post);

    // Map PostDTO to Post (if needed)
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "mapCandidateFromId")
    @Mapping(target = "election", source = "electionId", qualifiedByName = "mapElectionFromId")
    @Mapping(target = "party", source = "party", qualifiedByName = "mapPartyFromId")
    Post toEntity(PostDTO postDTO);

    // Map Post to PostResponseDTO.
    // Ignore likeCount and commentCount so they can be set later in the service.
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "party", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "likedByCurrentUser", constant = "false")
    PostResponseDTO toResponseDTO(Post post);

    // After mapping, decide which minimal DTO to set for candidate or party.
    @AfterMapping
    default void setCandidateAndParty(Post post, @MappingTarget PostResponseDTO dto) {
        if (post.getParty() != null) {
            dto.setParty(mapParty(post.getParty()));
            dto.setCandidate(null);
        } else if (post.getCandidate() != null) {
            dto.setCandidate(mapCandidate(post.getCandidate()));
            dto.setParty(null);
        } else {
            dto.setCandidate(null);
            dto.setParty(null);
        }
    }

    // Custom method to map Party to MinimalPartyDTO.
    default MinimalPartyDTO mapParty(Party party) {
        if (party == null) return null;
        MinimalPartyDTO dto = new MinimalPartyDTO();
        dto.setPartyId(party.getPartyId());
        dto.setName(party.getName());
        dto.setImageUrl(party.getImageUrl());
        return dto;
    }

    // Custom method to map Candidate to MinimalCandidateDTO.
    default MinimalCandidateDTO mapCandidate(Candidate candidate) {
        if (candidate == null) return null;
        MinimalCandidateDTO dto = new MinimalCandidateDTO();
        dto.setCandidateId(candidate.getCandidateId());
        // Check if the associated User is available.
        if (candidate.getUser() != null) {
            dto.setName(candidate.getUser().getName());
            dto.setImageUrl(candidate.getUser().getProfilePicture());
        } else {
            // If candidate.getUser() is null, set default values or leave them null.
            dto.setName(null);       // Or use a default like "Unknown Candidate"
            dto.setImageUrl(null);   // Or a default image URL if desired.
        }
        return dto;
    }

    // Custom mapping methods for converting candidate, election, and party IDs (if needed).
    @Named("mapCandidateFromId")
    default Candidate mapCandidateFromId(Long candidateId) {
        if (candidateId == null) return null;
        Candidate candidate = new Candidate();
        candidate.setCandidateId(candidateId);
        return candidate;
    }

    @Named("mapElectionFromId")
    default Election mapElectionFromId(Long electionId) {
        if (electionId == null) return null;
        Election election = new Election();
        election.setElectionId(electionId);
        return election;
    }

    @Named("mapPartyFromId")
    default Party mapPartyFromId(Long partyId) {
        if (partyId == null) return null;
        Party party = new Party();
        party.setPartyId(partyId);
        return party;
    }
}
