package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.PostDTO;
import com.example.e_voting_system.Model.DTO.PostResponseDTO;
import com.example.e_voting_system.Model.Entity.Candidate;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Post;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // Map Post to PostDTO
    @Mapping(source = "candidate.candidateId", target = "candidate")
    @Mapping(source = "election.electionId", target = "electionId")
    PostDTO toDTO(Post post);

    // Map PostDTO to Post
    @Mapping(target = "candidate", source = "candidate", qualifiedByName = "mapCandidateFromId")
    @Mapping(target = "election", source = "electionId", qualifiedByName = "mapElectionFromId")
    Post toEntity(PostDTO postDTO);

    // Map Post to PostResponseDTO for detailed response with additional fields
    @Mapping(source = "candidate.candidateId", target = "candidateId")
    @Mapping(source = "candidate.user.name", target = "candidateName")
    @Mapping(source = "candidate.user.profilePicture", target = "candidateProfilePicture")
    PostResponseDTO toResponseDTO(Post post);

    // Custom mapping methods
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
