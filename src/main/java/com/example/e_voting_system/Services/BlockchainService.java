package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.PartyRepository;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

@Service
public class BlockchainService {

    private final VotingSystem votingSystem;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;

    // Inject the VotingSystem bean
    public BlockchainService(VotingSystem votingSystem, ElectionRepository electionRepository, PartyRepository partyRepository) {
        this.votingSystem = votingSystem;
        this.electionRepository = electionRepository;
        this.partyRepository = partyRepository;
    }

    public void publishElectionToBlockchain(Election election, List<Party> parties) throws Exception {
        // Publish the election to the blockchain
        TransactionReceipt electionReceipt = votingSystem
                .createElection(BigInteger.valueOf(election.getElectionId()), election.getTitle())
                .send();

        String electionTransactionHash = electionReceipt.getTransactionHash();
        System.out.println("Election published with transaction hash: " + electionTransactionHash);

        // Save transaction hash for the election in the database
        election.setTransactionHash(electionTransactionHash);
        electionRepository.save(election);

        // Publish each party to the blockchain
        for (Party party : parties) {
            TransactionReceipt partyReceipt = votingSystem
                    .addParty(
                            BigInteger.valueOf(party.getPartyId()),
                            party.getName(),
                            BigInteger.valueOf(party.getElection().getElectionId())
                    )
                    .send();

            String partyTransactionHash = partyReceipt.getTransactionHash();
            System.out.println("Party published with transaction hash: " + partyTransactionHash);

            // Save transaction hash for the party in the database
            party.setTransactionHash(partyTransactionHash);
            partyRepository.save(party);
        }
    }
}
