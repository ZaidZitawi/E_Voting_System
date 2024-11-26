package com.example.e_voting_system.Model.DTO;


import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class LikeResponseDTO {
    private Long likeId;
    private Long userId;
    private String userName;
    private String userProfilePicture;
    private ZonedDateTime createdAt;
}