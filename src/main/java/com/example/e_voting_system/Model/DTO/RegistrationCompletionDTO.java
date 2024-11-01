package com.example.e_voting_system.Model.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistrationCompletionDTO {

    @NotNull(message = "Faculty ID is required")
    private Long facultyId;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

}

