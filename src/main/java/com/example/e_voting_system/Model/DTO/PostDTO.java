package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostDTO {
    private Long postId;
    private Long candidateId;
    private String content;
    private String mediaUrl;
    private ZonedDateTime createdAt;

}

