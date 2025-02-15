package com.example.e_voting_system.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electionId;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ElectionType type;

    @Column(nullable = false)
    private ZonedDateTime startDatetime;

    @Column(nullable = false)
    private ZonedDateTime endDatetime;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private Boolean isActive = true;

    @Column(name = "transaction_hash")
    private String transactionHash;

    @OneToMany(mappedBy = "election")
    private List<Party> parties;


    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
}
