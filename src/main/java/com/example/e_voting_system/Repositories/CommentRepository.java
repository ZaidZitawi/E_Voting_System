package com.example.e_voting_system.Repositories;


import com.example.e_voting_system.Model.Entity.Comment;
import com.example.e_voting_system.Model.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_PostId(Long postId);


    //find number of comments by post id
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.postId = :postId")
    int countByPost_PostId(@Param("postId") Long postId);


    //find number of comments by election id
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.election.electionId = :electionId")
    int countByElectionId(@Param("electionId") Long electionId);
}
