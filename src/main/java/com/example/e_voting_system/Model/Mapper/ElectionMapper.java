package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.ElectionType;
import com.example.e_voting_system.Model.Entity.Faculty;
import com.example.e_voting_system.Model.Entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ElectionMapper {

    // Mapping from Election entity to ElectionDTO
    @Mapping(source = "type.typeId", target = "typeId")
    @Mapping(source = "faculty.facultyId", target = "facultyId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    ElectionDTO toDTO(Election election);

    // Mapping from ElectionDTO to Election entity
    @Mapping(target = "type", source = "typeId", qualifiedByName = "mapTypeFromId")
    @Mapping(target = "faculty", source = "facultyId", qualifiedByName = "mapFacultyFromId")
    @Mapping(target = "department", source = "departmentId", qualifiedByName = "mapDepartmentFromId")
    Election toEntity(ElectionDTO electionDTO);

    // Custom mapping methods
    @Named("mapTypeFromId")
    default ElectionType mapTypeFromId(Long typeId) {
        if (typeId == null) return null;
        ElectionType type = new ElectionType();
        type.setTypeId(typeId);
        return type;
    }

    @Named("mapFacultyFromId")
    default Faculty mapFacultyFromId(Long facultyId) {
        if (facultyId == null) return null;
        Faculty faculty = new Faculty();
        faculty.setFacultyId(facultyId);
        return faculty;
    }

    @Named("mapDepartmentFromId")
    default Department mapDepartmentFromId(Long departmentId) {
        if (departmentId == null) return null;
        Department department = new Department();
        department.setDepartmentId(departmentId);
        return department;
    }
}
