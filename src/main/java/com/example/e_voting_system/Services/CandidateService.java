package com.example.e_voting_system.Services;

import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Model.DTO.CandidateDTO;
import com.example.e_voting_system.Model.DTO.CandidateSummaryDTO;
import com.example.e_voting_system.Model.Entity.Candidate;
import com.example.e_voting_system.Model.Entity.Post;
import com.example.e_voting_system.Model.Entity.Role;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Model.Mapper.CandidateMapper;
import com.example.e_voting_system.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CandidateMapper candidateMapper;
    private final RoleRepository roleRepository;

    public CandidateService(CandidateRepository candidateRepository, PostRepository postRepository,
                            CommentRepository commentRepository,
                            LikeRepository likeRepository,
                            UserRepository userRepository,
                            CandidateMapper candidateMapper,
                            RoleRepository roleRepository) {
        this.candidateRepository = candidateRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.candidateMapper = candidateMapper;
        this.roleRepository=roleRepository;
    }

    public List<CandidateSummaryDTO> getCandidatesByElection(Long electionId) {
        // Fetch candidates for the given election
        List<Candidate> candidates = candidateRepository.findByElection_ElectionId(electionId);

        // Map candidates to CandidateSummaryDTO using candidateMapper
        return candidates.stream()
                .map(candidateMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public CandidateDTO getCandidateDetails(Long candidateId) {
        // Fetch the candidate by ID
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));

        // Map candidate to CandidateDTO using candidateMapper
        CandidateDTO candidateDTO = candidateMapper.toDTO(candidate);

        // Fetch and populate additional fields if needed
        List<Post> posts = postRepository.findByCandidate_CandidateId(candidateId);
        candidateDTO.setPosts(posts);

        int totalComments = posts.stream()
                .mapToInt(post -> commentRepository.countByElectionId(post.getElection().getElectionId()))
                .sum();
        candidateDTO.setTotalComments(totalComments);

        int totalLikes = posts.stream()
                .mapToInt(post -> likeRepository.countByElectionId(post.getElection().getElectionId()))
                .sum();
        candidateDTO.setTotalLikes(totalLikes);

        return candidateDTO;
    }

    public void deleteCandidate(Long candidateId) {
        // Find the candidate by ID
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));

        // Get the user associated with the candidate
        User user = candidate.getUser();

        // Delete the candidate record
        candidateRepository.delete(candidate);
        user.setRole(new Role(1L, "ROLE_USER"));

        // Save the updated user
        userRepository.save(user);
    }

    public List<CandidateSummaryDTO> getCandidatesByParty(Long partyId) {
        List<Candidate> candidates = candidateRepository.findByParty_PartyId(partyId);
        return candidates.stream()
                .map(candidateMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public CandidateDTO getCandidateByUserId(Long userId) {
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found for user ID: " + userId));
        return candidateMapper.toDTO(candidate);
    }


    @Transactional
    public void deleteCandidateById(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));

        User candidateUser = candidate.getUser();

        // Fetch the user role (assuming roleId of 1 corresponds to ROLE_USER)
        Role userRole = roleRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("User role not found in the database."));

        // Reassign the candidate's user role to ROLE_USER
        candidateUser.setRole(userRole);
        userRepository.save(candidateUser);

        // Delete the candidate
        candidateRepository.delete(candidate);
    }
}