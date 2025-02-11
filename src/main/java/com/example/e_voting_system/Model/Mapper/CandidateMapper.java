package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.CandidateDTO;
import com.example.e_voting_system.Model.DTO.CandidateSummaryDTO;
import com.example.e_voting_system.Model.Entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(source = "user.name", target = "candidateName")
    @Mapping(target = "posts", ignore = true) // Posts should be populated separately
    @Mapping(target = "totalComments", ignore = true) // Computed field
    @Mapping(target = "totalLikes", ignore = true) // Computed field
    @Mapping(target = "totalVotes", ignore = true) // Computed field
    CandidateDTO toDTO(Candidate candidate);

    @Mapping(target = "user", ignore = true) // User is populated separately
    @Mapping(target = "election", ignore = true) // Election is populated separately
    @Mapping(target = "candidateId", ignore = true) // ID is auto-generated
    Candidate toEntity(CandidateDTO candidateDTO);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(source = "candidateId", target = "candidateId")
    @Mapping(source = "user.name", target = "candidateName")
    @Mapping(source = "user.profilePicture", target = "profilePicture")
    @Mapping(source = "party.name", target = "party")
    CandidateSummaryDTO toSummaryDTO(Candidate candidate);
}