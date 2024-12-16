package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostDTO {
    private Long postId;
    private Long candidate;
    private Long electionId; 
    private String content;
    private MultipartFile media;
    private ZonedDateTime createdAt;
}

