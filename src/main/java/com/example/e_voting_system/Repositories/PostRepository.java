package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Comment;
import com.example.e_voting_system.Model.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByElection_ElectionId(Long electionId);

    List<Post> findByCandidate_CandidateId(Long candidateId);




}
