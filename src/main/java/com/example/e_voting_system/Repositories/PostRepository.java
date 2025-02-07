package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Comment;
import com.example.e_voting_system.Model.Entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByElection_ElectionId(Long electionId);

    List<Post> findByCandidate_CandidateId(Long candidateId);

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN p.likes l GROUP BY p ORDER BY COUNT(l) DESC")
    Page<Post> findAllOrderByLikes(Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN p.comments c GROUP BY p ORDER BY COUNT(c) DESC")
    Page<Post> findAllOrderByComments(Pageable pageable);
}
