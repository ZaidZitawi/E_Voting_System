package com.example.e_voting_system.Repositories;




import com.example.e_voting_system.Model.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Find departments by faculty
    List<Department> findByFaculty_FacultyId(Long facultyId);
}

