package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.Entity.Election;
import com.example.e_voting_system.Model.Entity.Party;
import com.example.e_voting_system.Repositories.ElectionRepository;
import com.example.e_voting_system.Repositories.PartyRepository;
import com.example.e_voting_system.Utils.BlockchainUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;


import java.math.BigInteger;
import java.util.List;

@Service
public class BlockchainService {

    private static final Logger logger = LoggerFactory.getLogger(BlockchainService.class);
    private final VotingSystem votingSystem;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;
    private final Web3j web3j;
    private final Credentials credentials;

    public BlockchainService(VotingSystem votingSystem,
                             ElectionRepository electionRepository,
                             PartyRepository partyRepository,
                             Web3j web3j,
                             Credentials credentials) {
        this.votingSystem = votingSystem;
        this.electionRepository = electionRepository;
        this.partyRepository = partyRepository;
        this.web3j = web3j;
        this.credentials = credentials;
    }

    public void publishElectionToBlockchain(Election election, List<Party> parties) throws Exception {
        try {
            // 1) Fetch current nonce for debugging
            BigInteger currentNonce = BlockchainUtils.getCurrentNonce(web3j, credentials.getAddress());
            System.out.println("Current nonce for " + credentials.getAddress() + ": " + currentNonce);

            // 2) Publish the election
            TransactionReceipt electionReceipt = votingSystem
                    .createElection(BigInteger.valueOf(election.getElectionId()), election.getTitle())
                    .send(); // This waits until the transaction is mined

            // 3) Check if the transaction was successful
            if (!electionReceipt.isStatusOK()) {
                System.err.println("Election transaction failed with status: " + electionReceipt.getStatus());
                throw new TransactionException("Failed to create election. Transaction failed.");
            }

            // 4) Store transaction hash
            String electionTransactionHash = electionReceipt.getTransactionHash();
            System.out.println("Election published with transaction hash: " + electionTransactionHash);
            election.setTransactionHash(electionTransactionHash);
            electionRepository.save(election);

            // 5) Publish each party
            for (Party party : parties) {
                TransactionReceipt partyReceipt = votingSystem
                        .addParty(
                                BigInteger.valueOf(party.getPartyId()),
                                party.getName(),
                                BigInteger.valueOf(party.getElection().getElectionId())
                        )
                        .send(); // Waits until mined

                // Check if the transaction was successful
                if (!partyReceipt.isStatusOK()) {
                    System.err.println("Party transaction failed with status: " + partyReceipt.getStatus());
                    throw new TransactionException("Failed to add party. Transaction failed.");
                }

                // Store transaction hash
                String partyTransactionHash = partyReceipt.getTransactionHash();
                System.out.println("Party published with transaction hash: " + partyTransactionHash);
                party.setTransactionHash(partyTransactionHash);
                partyRepository.save(party);
            }
        } catch (Exception e) {
            logger.error("Error publishing to blockchain: {}", e.getMessage(), e);
            throw e;
        }
    }
}
