package com.example.e_voting_system.Model.DTO;

//used when sending notifications to users

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {
    private Long userId;
    private String message;
}

