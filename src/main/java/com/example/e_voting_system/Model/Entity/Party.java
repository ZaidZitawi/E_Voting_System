package com.example.e_voting_system.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "parties")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User campaignManager;

    private String bio;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @OneToMany(mappedBy = "party")
    private List<Candidate> candidates;

    @OneToMany(mappedBy = "party")
    private List<Post> posts;

    @Column(name = "transaction_hash")
    private String transactionHash;

}
