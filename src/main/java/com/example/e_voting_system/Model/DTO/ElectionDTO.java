package com.example.e_voting_system.Model.DTO;

import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class ElectionDTO {
    private Long electionId;
    private String title;
    private String description;
    private Long typeId;           // Keeping typeId, createdBy, etc., but map them later
    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
    private String imageUrl;
    private Long facultyId;
    private Long departmentId;
    private Boolean isActive;
}
