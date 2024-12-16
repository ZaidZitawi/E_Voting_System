package com.example.e_voting_system.Repositories;


import com.example.e_voting_system.Model.Entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    int countByCandidate_CandidateId(Long candidateId);



}
