package com.example.e_voting_system.Utils;


import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.io.IOException;
import java.math.BigInteger;

public class BlockchainUtils {

    private BlockchainUtils() {
        // private constructor to prevent instantiation
    }

    public static BigInteger getCurrentNonce(Web3j web3j, String walletAddress) throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                walletAddress, DefaultBlockParameterName.PENDING
        ).send();

        return ethGetTransactionCount.getTransactionCount();
    }
}
