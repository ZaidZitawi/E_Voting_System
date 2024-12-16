package com.example.e_voting_system.Services;


import com.example.e_voting_system.Model.DTO.FacultyDTO;
import com.example.e_voting_system.Model.Entity.Faculty;
import com.example.e_voting_system.Model.Mapper.FacultyMapper;
import com.example.e_voting_system.Repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

    public List<FacultyDTO> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(facultyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FacultyDTO getFacultyById(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        return facultyMapper.toDTO(faculty);
    }
}
