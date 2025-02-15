package com.example.e_voting_system.Model.DTO;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

@Data
public class ElectionUpdateDTO {
    private Long electionId;
    private String title;
    private String description;
    private Long typeId;
    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
    private transient MultipartFile imageFile;
    private String imageUrl;
    private Long facultyId;
    private Long departmentId;
    private Boolean isActive;
}
