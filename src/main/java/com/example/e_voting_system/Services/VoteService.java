package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Model.Entity.Vote;
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

    public VoteService(
            VotingSystem votingSystem,
            VoteRepository voteRepository,
            UserRepository userRepository,
            ElectionRepository electionRepository,
            PartyRepository partyRepository) {
        this.votingSystem = votingSystem;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.electionRepository = electionRepository;
        this.partyRepository = partyRepository;
    }

    @Transactional
    public String castVote(Long voterId, Long electionId, Long partyId) throws Exception {
        // Validate user
        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new IllegalArgumentException("Voter not found"));

        // Validate election
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new IllegalArgumentException("Election not found"));

        // Validate party
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("Party not found"));

        if (!party.getElection().getElectionId().equals(electionId)) {
            throw new IllegalArgumentException("Party does not belong to the specified election");
        }

        // Check for duplicate voting
        if (voteRepository.existsByVoterAndElection(voter, election)) {
            throw new IllegalArgumentException("Voter has already cast a vote in this election");
        }

        // Interact with the blockchain
        TransactionReceipt receipt = votingSystem.castVote(
                BigInteger.valueOf(electionId),
                BigInteger.valueOf(partyId)
        ).send();

        // Save the vote in the database
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setParty(party);
        vote.setTransactionHash(receipt.getTransactionHash());
        voteRepository.save(vote);

        return receipt.getTransactionHash();
    }
}
