package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;


@Getter
@Setter
public class CommentDTO {
    private Long commentId;
    private Long userId;
    private Long postId;
    private String content;
    private ZonedDateTime createdAt;


}
