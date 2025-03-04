package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Comment;
import com.example.e_voting_system.Model.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByPost_PostId(Long postId);

    //find number of likes by post id
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.postId = :postId")
    int countByPost_PostId(@Param("postId") Long postId);

    //find number of likes by election id
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.election.electionId = :electionId")
    int countByElectionId(@Param("electionId") Long electionId);

    boolean existsByPost_PostIdAndUser_UserId(Long postId, Long userId);

    Optional<Like> findByPost_PostIdAndUser_UserId(Long postId, Long userId);



}

