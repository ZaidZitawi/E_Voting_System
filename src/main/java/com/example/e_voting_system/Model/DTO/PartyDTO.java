package com.example.e_voting_system.Model.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartyDTO {
    private Long partyId;
    private String name;
    private String bio;
    private String imageUrl;
    private Long campaignManagerId;
    private Long electionId;
}
