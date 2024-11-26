package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT c FROM Candidate c WHERE c.user.email = :email")
    Optional<Candidate> findByUserEmail(@Param("email") String email);


}
