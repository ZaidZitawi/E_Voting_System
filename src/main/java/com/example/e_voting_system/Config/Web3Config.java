package com.example.e_voting_system.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3Config {

    @Bean
    public Web3j web3j() {
        //Infura API to interact with Sepolia Etheruim node
        return Web3j.build(new HttpService("https://sepolia.infura.io/v3/957f1ca772d24c7fb7a9b8b97f42f77c"));
    }
}
