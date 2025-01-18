package com.example.e_voting_system.Config;

import com.example.e_voting_system.Services.VotingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class BlockchainConfig {

    @Value("${wallet.private.key}")
    private String privateKey;

    @Bean
    public Credentials credentials() {
        // Create Credentials from the private key
        Credentials creds = Credentials.create(privateKey);
        System.out.println("Using credentials for address: " + creds.getAddress());
        return creds;
    }

    /**
     * Instantiate a TransactionManager that uses a PollingTransactionReceiptProcessor.
     * - Poll every 5 seconds, up to 36 attempts => 180 seconds max.
     * - Chain ID set to 80002 for Polygon Amoy.
     */
    @Bean
    public TransactionManager txManager(Web3j web3j, Credentials credentials) {
        int pollingIntervalMs = 5000; // 5 seconds
        int maxAttempts = 36;        // up to 180 seconds total
        PollingTransactionReceiptProcessor receiptProcessor =
                new PollingTransactionReceiptProcessor(web3j, pollingIntervalMs, maxAttempts);

        // chainId = 80002 for Polygon Amoy
        int chainId = 80002;

        // Use the RawTransactionManager with the PollingTransactionReceiptProcessor
        return new RawTransactionManager(
                web3j,
                credentials,
                chainId,
                receiptProcessor
        );
    }

    /**
     * Configure a custom gas provider:
     * - Base Gas Price = 51 Gwei
     * - Priority Fee   = 25 Gwei
     * - Total Gas Price = 76 Gwei
     * - Gas Limit = 3_000_000 (example)
     */
    @Bean
    public ContractGasProvider contractGasProvider() {
        BigInteger baseGasPrice = convertGweiToWei(51);    // 51 Gwei
        BigInteger priorityGasPrice = convertGweiToWei(25); // 25 Gwei
        BigInteger totalGasPrice = baseGasPrice.add(priorityGasPrice); // 76 Gwei

        BigInteger gasLimit = BigInteger.valueOf(3_000_000); // Adjust as needed

        return new StaticGasProvider(totalGasPrice, gasLimit);
    }

    /**
     * Load the VotingSystem contract using the TransactionManager and GasProvider beans.
     */
    @Bean
    public VotingSystem votingSystem(Web3j web3j,
                                     TransactionManager txManager,
                                     ContractGasProvider gasProvider) {
        // Make sure this is your correct deployed contract address on Amoy
        String contractAddress = "0x4E0121aF93679B40690DEA62652f0232CB5ecE93";

        // Load the contract using the custom transaction manager and gas provider
        return VotingSystem.load(contractAddress, web3j, txManager, gasProvider);
    }

    /**
     * Utility to convert Gwei to Wei
     */
    private BigInteger convertGweiToWei(long gwei) {
        return BigInteger.valueOf(gwei).multiply(BigInteger.valueOf(1_000_000_000L));
    }
}
