package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.VoteDTO;
import com.example.e_voting_system.Model.Entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface VoteMapper{

    @Mapping(source = "voter.userId", target = "voterId")
    @Mapping(source = "election.electionId", target = "electionId")
    @Mapping(source = "party.partyId", target = "candidateId")
    @Mapping(source = "characterName", target = "characterName")
    VoteDTO toDTO(Vote vote);

    @Mapping(source = "voterId", target = "voter.userId")
    @Mapping(source = "electionId", target = "election.electionId")
    @Mapping(source = "candidateId", target = "party.partyId")
    Vote toEntity(VoteDTO voteDTO);
}