package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateSummaryDTO {
    private Long candidateId;
    private Long userId;
    private String candidateName;
    private String profilePicture;
    private String party;
}
