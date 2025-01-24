package com.example.e_voting_system.Repositories;


import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Model.Entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterAndElection(User voter, Election election);

    @Query("SELECT v FROM Vote v WHERE v.voter.userId = :userId AND v.election.electionId = :electionId")
    Vote findByVoterAndElection(@Param("userId") Long userId, @Param("electionId") Long electionId);


}
