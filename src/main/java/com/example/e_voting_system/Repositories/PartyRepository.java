package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByElection_ElectionId(Long electionId);
}