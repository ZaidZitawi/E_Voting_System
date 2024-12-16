package com.example.e_voting_system.Model.Mapper;


import com.example.e_voting_system.Model.DTO.FacultyDTO;
import com.example.e_voting_system.Model.Entity.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FacultyMapper {


    FacultyDTO toDTO(Faculty faculty);

    Faculty toEntity(FacultyDTO facultyDTO);
}
