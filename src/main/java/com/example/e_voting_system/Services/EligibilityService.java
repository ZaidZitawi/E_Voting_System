package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityService {

    private final UserRepository userRepository;
    private final ElectionRepository electionRepository; // Inject ElectionRepository

    @Autowired
    public EligibilityService(UserRepository userRepository, ElectionRepository electionRepository) {
        this.userRepository = userRepository;
        this.electionRepository = electionRepository;
    }

    // Existing method to get eligible users for an election
    public List<User> getEligibleUsers(Election election) {
        Long facultyId = election.getFaculty() != null ? election.getFaculty().getFacultyId() : null;
        Long departmentId = election.getDepartment() != null ? election.getDepartment().getDepartmentId() : null;

        return userRepository.findEligibleUsers(facultyId, departmentId);
    }

    // New method to get eligible elections for a user
    public List<Election> getEligibleElectionsForUser(User user) {
        Long facultyId = user.getFaculty() != null ? user.getFaculty().getFacultyId() : null;
        Long departmentId = user.getDepartment() != null ? user.getDepartment().getDepartmentId() : null;

        return electionRepository.findByFacultyAndDepartment(facultyId, departmentId);
    }
}
