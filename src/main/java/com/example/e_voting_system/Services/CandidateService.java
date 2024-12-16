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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;


    public CandidateService(CandidateRepository candidateRepository, PostRepository postRepository,
                            CommentRepository commentRepository, LikeRepository likeRepository,
                            VoteRepository voteRepository, UserRepository userRepository) {
        this.candidateRepository = candidateRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }


    public List<CandidateSummaryDTO> getCandidatesByElection(Long electionId) {
        // Fetch candidates for the given election
        List<Candidate> candidates = candidateRepository.findByElection_ElectionId(electionId);

        // Map candidates to CandidateSummaryDTO
        return candidates.stream().map(candidate -> {
            CandidateSummaryDTO dto = new CandidateSummaryDTO();
            dto.setCandidateId(candidate.getCandidateId());
            dto.setCandidateName(candidate.getUser().getName());
            dto.setProfilePicture(candidate.getUser().getProfilePicture());
            dto.setParty(candidate.getParty());
            return dto;
        }).collect(Collectors.toList());
    }



    public CandidateDTO getCandidateDetails(Long candidateId) {
        // Fetch the candidate by ID
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateId));

        // Map candidate to CandidateDTO
        CandidateDTO candidateDTO = CandidateMapper.INSTANCE.toDTO(candidate);

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

        int totalVotes = voteRepository.countByCandidate_CandidateId(candidateId);
        candidateDTO.setTotalVotes(totalVotes);

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
}
