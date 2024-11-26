package com.example.e_voting_system.Model.Mapper;


import com.example.e_voting_system.Model.DTO.UserDTO;
import com.example.e_voting_system.Model.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(source = "role.roleId", target = "roleId")
    @Mapping(source = "faculty.facultyId", target = "facultyId")
    @Mapping(source = "department.departmentId", target = "departmentId")
    UserDTO toDTO(User user);

    @Mapping(source = "roleId", target = "role.roleId")
    @Mapping(source = "facultyId", target = "faculty.facultyId")
    @Mapping(source = "departmentId", target = "department.departmentId")
    User toEntity(UserDTO userDTO);
}