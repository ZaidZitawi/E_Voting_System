package com.example.e_voting_system.Config;

import com.example.e_voting_system.Services.VotingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class BlockchainConfig {

    @Value("${wallet.private.key}")
    private String privateKey;

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }

    @Bean
    public ContractGasProvider contractGasProvider() {
        return new DefaultGasProvider();
    }

    @Bean
    public VotingSystem votingSystem(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        String contractAddress = "0x9D877b9a239Cb36Ad4B4e9E73b30D7B0611348fD";
        return VotingSystem.load(contractAddress, web3j, credentials, contractGasProvider);
    }
}