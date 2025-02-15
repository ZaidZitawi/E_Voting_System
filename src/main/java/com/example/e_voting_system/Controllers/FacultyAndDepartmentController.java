package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Model.DTO.DepartmentDTO;
import com.example.e_voting_system.Model.DTO.FacultyDTO;
import com.example.e_voting_system.Services.DepartmentService;
import com.example.e_voting_system.Services.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/faculty-and-department")
@RestController
public class FacultyAndDepartmentController {


    private final FacultyService facultyService;
    private final DepartmentService departmentService;

    public FacultyAndDepartmentController(FacultyService facultyService, DepartmentService departmentService) {
        this.facultyService = facultyService;
        this.departmentService = departmentService;
    }


    @GetMapping("/faculties")
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        List<FacultyDTO> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }


    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }


    @GetMapping("/faculties/{facultyId}/departments")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByFacultyId(@PathVariable Long facultyId) {
        List<DepartmentDTO> departments = departmentService.getDepartmentsByFacultyId(facultyId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/faculties/{facultyId}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long facultyId) {
        FacultyDTO faculty = facultyService.getFacultyById(facultyId);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        DepartmentDTO department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }
}
