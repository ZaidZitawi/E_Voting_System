package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.DepartmentDTO;
import com.example.e_voting_system.Model.Entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(source = "faculty.facultyId", target = "facultyId")
    DepartmentDTO toDTO(Department department);

    @Mapping(source = "facultyId", target = "faculty.facultyId")
    Department toEntity(DepartmentDTO departmentDTO);
}
