package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
public class LikeDTO {
    private Long likeId;
    private Long userId;
    private Long postId;
    private ZonedDateTime createdAt;
}

