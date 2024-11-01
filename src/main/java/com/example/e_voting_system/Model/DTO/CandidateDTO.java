package com.example.e_voting_system.Model.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDTO {
    private Long candidateId;
    private Long userId;
    private Long electionId;
    private String campaignDetails;

}
