package com.example.e_voting_system.Model.DTO;


import com.example.e_voting_system.Model.Entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CandidateDTO {
    private Long candidateId;
    private String candidateName;
    private String campaignDetails;
    private List<Post> posts;
    private int totalComments;
    private int totalLikes;
    private int totalVotes;
}
