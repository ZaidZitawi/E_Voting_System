package com.example.e_voting_system.Model.DTO;



import com.example.e_voting_system.Model.Entity.Department;
import com.example.e_voting_system.Model.Entity.Faculty;
import lombok.Data;
import java.time.ZonedDateTime;

@Data
public class ElectionDTO2 {
    private Long electionId;
    private String title;
    private String description;
    private Long typeId;
    private ZonedDateTime startDatetime;
    private ZonedDateTime endDatetime;
    private String imageUrl;
    private Faculty faculty;
    private Department department;
    private Boolean isActive;
}
