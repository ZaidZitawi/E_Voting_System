package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByElection_ElectionId(Long electionId);

    @Query("SELECT p FROM Party p WHERE p.campaignManager.userId = :userId")
    Optional<Party> findByUserId(@Param("userId") Long userId);
}