package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.DTO.VoteDTO;
import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Model.Entity.Vote;
import com.example.e_voting_system.Model.Mapper.VoteMapper;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.PartyRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import com.example.e_voting_system.Repositories.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Service
public class VoteService {

    private final VotingSystem votingSystem;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;
    private final EligibilityService eligibilityService;
    private final SillyNameService sillyNameService;
    private final VoteMapper voteMapper;

    public VoteService(
            VotingSystem votingSystem,
            VoteRepository voteRepository,
            UserRepository userRepository,
            ElectionRepository electionRepository,
            PartyRepository partyRepository,
            EligibilityService eligibilityService,
            SillyNameService sillyNameService,
            VoteMapper voteMapper
    ) {
        this.votingSystem = votingSystem;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.electionRepository = electionRepository;
        this.partyRepository = partyRepository;
        this.eligibilityService = eligibilityService;
        this.sillyNameService = sillyNameService;
        this.voteMapper = voteMapper;
    }

    /**
     * Cast a vote by interacting with the blockchain and saving details in DB.
     * @param email         The email (from JWT) of the user who is voting.
     * @param electionId    The ID of the election.
     * @param partyId       The ID of the party.
     * @param characterName The character name assigned to the vote (could be "anonymous").
     * @return             Transaction hash.
     */
    @Transactional
    public String castVote(String email, Long electionId, Long partyId, String characterName) throws Exception {
        // 1. Validate user from email
        User voter = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Voter not found by email: " + email));

        // 2. Validate election
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new IllegalArgumentException("Election not found"));

        // 3. Validate party
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found"));

        // 3a. Ensure the party belongs to the correct election
        if (!party.getElection().getElectionId().equals(electionId)) {
            throw new IllegalArgumentException("Party does not belong to the specified election");
        }

        // 4. Check if user already voted
        if (voteRepository.existsByVoterAndElection(voter, election)) {
            throw new IllegalArgumentException("Voter has already cast a vote in this election");
        }

        // 5. Check eligibility
        boolean isEligible = eligibilityService.isUserEligibleForElection(voter, election);
        if (!isEligible) {
            throw new IllegalArgumentException("Voter is not eligible for this election based on faculty/department");
        }

        // 6. If characterName is "anonymous" or blank, pick a random silly name
        if (characterName == null || characterName.trim().isEmpty()
                || "anonymous".equalsIgnoreCase(characterName.trim())) {
            characterName = sillyNameService.getRandomSillyName(); // or inline logic if you prefer
        }

        // 7. Interact with the blockchain
        TransactionReceipt receipt = votingSystem.castVote(
                BigInteger.valueOf(electionId),
                BigInteger.valueOf(partyId),
                BigInteger.valueOf(voter.getUserId()),
                characterName
        ).send();

        // 8. Persist the vote (including the final character name)
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setParty(party);
        vote.setTransactionHash(receipt.getTransactionHash());
        vote.setCharacterName(characterName); // new field in the DB
        voteRepository.save(vote);

        return receipt.getTransactionHash();
    }

    public VoteDTO getMyVote(String email, Long electionId) {
        // 1) Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found by email: " + email));

        // 2) Find vote in DB
        Vote vote = voteRepository.findByVoterAndElection(user.getUserId(), electionId);
        if (vote == null) return null;

        // 3) Convert to VoteDTO
        return voteMapper.toDTO(vote);
    }

}
