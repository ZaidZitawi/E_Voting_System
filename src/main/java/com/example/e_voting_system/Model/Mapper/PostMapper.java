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
    PostDTO toDTO(Post post);

    // Map PostDTO to Post (if needed)
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "mapCandidateFromId")
    @Mapping(target = "election", source = "electionId", qualifiedByName = "mapElectionFromId")
    Post toEntity(PostDTO postDTO);

    // Map Post to PostResponseDTO
    // Ignore likeCount and commentCount so they can be set later in the service.
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "party", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    // Default likedByCurrentUser to false (service will update this)
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

    // Custom method to map Party to MinimalPartyDTO
    default MinimalPartyDTO mapParty(Party party) {
        if (party == null) return null;
        MinimalPartyDTO dto = new MinimalPartyDTO();
        dto.setPartyId(party.getPartyId());
        dto.setName(party.getName());
        dto.setImageUrl(party.getImageUrl());
        return dto;
    }

    // Custom method to map Candidate to MinimalCandidateDTO
    default MinimalCandidateDTO mapCandidate(Candidate candidate) {
        if (candidate == null) return null;
        MinimalCandidateDTO dto = new MinimalCandidateDTO();
        dto.setCandidateId(candidate.getCandidateId());
        // Assume the candidate's name and profile image are derived from the associated User entity:
        dto.setName(candidate.getUser().getName());
        dto.setImageUrl(candidate.getUser().getProfilePicture());
        return dto;
    }

    // Custom mapping methods for converting candidate and election IDs (if needed)
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
}
