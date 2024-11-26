package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
public class VoteDTO {
    private Long voteId;
    private Long voterId;
    private Long electionId;
    private Long candidateId;
    private String transactionHash;
    private ZonedDateTime createdAt;

    // Constructors, Getters, Setters
}
