package com.example.e_voting_system.Services;


import com.example.e_voting_system.Model.DTO.DepartmentDTO;
import com.example.e_voting_system.Model.Entity.Department;
import com.example.e_voting_system.Model.Mapper.DepartmentMapper;
import com.example.e_voting_system.Repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDTO> getDepartmentsByFacultyId(Long facultyId) {
        List<Department> departments = departmentRepository.findByFacultyFacultyId(facultyId);
        return departments.stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return departmentMapper.toDTO(department);
    }
}
