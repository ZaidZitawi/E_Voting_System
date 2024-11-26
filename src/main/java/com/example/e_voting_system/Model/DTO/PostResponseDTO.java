package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;


@Getter
@Setter
public class PostResponseDTO {
    private Long postId;
    private Long candidateId;
    private String candidateName;
    private String candidateProfilePicture;
    private String content;
    private String mediaUrl;
    private ZonedDateTime createdAt;
    private int commentCount;
    private int likeCount;    // Number of likes
    private boolean likedByCurrentUser; // Whether the current user has liked this post
}