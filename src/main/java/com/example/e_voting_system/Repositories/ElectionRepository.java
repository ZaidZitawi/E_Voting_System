package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long>, JpaSpecificationExecutor<Election> {


    // Custom query to search for elections based on title
    @Query("SELECT e FROM Election e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Election> findByTitleContainingIgnoreCase(String searchTerm);



    // Fetch random elections without any exclusions
    @Query(value = "SELECT * FROM elections e ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Election> findRandomElections(@Param("limit") int limit);

    // Fetch random elections excluding specific IDs
    @Query(value = "SELECT * FROM elections e WHERE e.election_id NOT IN (:excludedIds) ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Election> findRandomElectionsExcluding(@Param("limit") int limit, @Param("excludedIds") List<Long> excludedIds);


    //query to get elections by faculty and department
    @Query("SELECT e FROM Election e WHERE " +
            "(:facultyId IS NULL OR e.faculty.facultyId = :facultyId) AND " +
            "(:departmentId IS NULL OR e.department.departmentId = :departmentId)")
    List<Election> findByFacultyAndDepartment(@Param("facultyId") Long facultyId,
                                              @Param("departmentId") Long departmentId);


    @Query("SELECT e.electionId FROM Election e JOIN e.parties p WHERE p.campaignManager.userId = :userId")
    Optional<Long> findElectionIdByUserId(@Param("userId") Long userId);

    @Query("SELECT c.election.electionId FROM Candidate c WHERE c.user.userId = :userId")
    Optional<Long> findElectionIdByCandidateUserId(@Param("userId") Long userId);
}

