package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimalCandidateDTO {
    private Long candidateId;
    private String name;
    private String imageUrl;
}
