package com.example.e_voting_system.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class NotificationDTO {
    private Long notificationId;
    private Long userId;
    private String message;
    private Boolean isRead;
    private ZonedDateTime createdAt;
}
