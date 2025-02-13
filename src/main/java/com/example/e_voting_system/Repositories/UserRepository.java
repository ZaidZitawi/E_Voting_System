package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);



    //Searches the database for a user whose email field matches
    // the provided email and whose verificationCode field matches the provided verification code.
    Optional<User> findByEmailAndVerificationCode(String email, String verificationCode);


    @Query("""
    SELECT u
    FROM User u
    WHERE
      (:facultyId IS NULL OR u.faculty.facultyId = :facultyId) AND
      (:departmentId IS NULL OR u.department.departmentId = :departmentId)
""")
    List<User> findEligibleUsers(
            @Param("facultyId") Long facultyId,
            @Param("departmentId") Long departmentId
    );

    @Query("SELECT COUNT(u) FROM User u")
    Long countTotalUsers();

    @Query("SELECT u.role.roleName, COUNT(u) FROM User u GROUP BY u.role.roleName")
    List<Object[]> countUsersByRole();
}