package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long>, JpaSpecificationExecutor<Election> {

    // Custom query to search for elections based on title
    @Query("SELECT e FROM Election e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Election> findByTitleContainingIgnoreCase(String searchTerm);
}

