package com.example.e_voting_system.Model.Mapper;

import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.ElectionType;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Model.Entity.Faculty;
import com.example.e_voting_system.Model.Entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ElectionMapper {

    ElectionMapper INSTANCE = Mappers.getMapper(ElectionMapper.class);

    // Mapping from Election entity to ElectionDTO with nested fields
    @Mapping(source = "type", target = "typeId", qualifiedByName = "mapTypeId")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "mapUserId")
    @Mapping(source = "faculty", target = "facultyId", qualifiedByName = "mapFacultyId")
    @Mapping(source = "department", target = "departmentId", qualifiedByName = "mapDepartmentId")
    ElectionDTO toDTO(Election election);

    // Mapping from ElectionDTO to Election entity with nested fields
    @Mapping(target = "type.typeId", source = "typeId")
    @Mapping(target = "createdBy.userId", source = "createdBy")
    @Mapping(target = "faculty.facultyId", source = "facultyId")
    @Mapping(target = "department.departmentId", source = "departmentId")
    Election toEntity(ElectionDTO electionDTO);

    // Custom mapping methods for nested field IDs
    @Named("mapTypeId")
    default Long mapTypeId(ElectionType type) {
        return type != null ? type.getTypeId() : null;
    }

    @Named("mapUserId")
    default Long mapUserId(User user) {
        return user != null ? user.getUserId() : null;
    }

    @Named("mapFacultyId")
    default Long mapFacultyId(Faculty faculty) {
        return faculty != null ? faculty.getFacultyId() : null;
    }

    @Named("mapDepartmentId")
    default Long mapDepartmentId(Department department) {
        return department != null ? department.getDepartmentId() : null;
    }
}
