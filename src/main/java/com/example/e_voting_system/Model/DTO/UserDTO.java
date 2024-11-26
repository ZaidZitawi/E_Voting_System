package com.example.e_voting_system.Model.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String profilePicture;
    private String bio;
    private Boolean verified;
    private Long roleId;
    private Long facultyId;
    private Long departmentId;
}
