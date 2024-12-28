package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.PartyDTO;
import com.example.e_voting_system.Model.Entity.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartyMapper {


    @Mapping(source = "campaignManagerId", target = "campaignManager.userId")
    @Mapping(source = "electionId", target = "election.electionId")
    Party toEntity(PartyDTO partyDTO);

    @Mapping(source = "campaignManager.userId", target = "campaignManagerId")
    @Mapping(source = "election.electionId", target = "electionId")
    PartyDTO toDTO(Party party);
}
