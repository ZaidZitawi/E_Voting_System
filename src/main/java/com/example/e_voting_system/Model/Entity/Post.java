package com.example.e_voting_system.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;


    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;


    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @Column(nullable = false)
    private String content;

    private String mediaUrl;

    @Column(updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    // NEW: Relationship to likes
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

}
