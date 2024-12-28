package com.example.e_voting_system.Services;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class VotingSystem extends Contract {
    public static final String BINARY = "0x6080604052348015600f57600080fd5b506114a78061001f6000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063b6c8ecb21161005b578063b6c8ecb214610127578063c6e36a3214610143578063df8ecf5414610175578063ff981099146101a557610088565b80632c0a3f891461008d5780635e6fef01146100a957806370651a18146100d95780637da09d941461010b575b600080fd5b6100a760048036038101906100a291906109be565b6101d5565b005b6100c360048036038101906100be91906109fe565b610457565b6040516100d09190610abb565b60405180910390f35b6100f360048036038101906100ee91906109fe565b6104fd565b60405161010293929190610aec565b60405180910390f35b61012560048036038101906101209190610c5f565b6105af565b005b610141600480360381019061013c9190610cbb565b610672565b005b61015d600480360381019061015891906109be565b610811565b60405161016c93929190610d6b565b60405180910390f35b61018f600480360381019061018a91906109fe565b610878565b60405161019c9190610da2565b60405180910390f35b6101bf60048036038101906101ba91906109fe565b610898565b6040516101cc9190610ecc565b60405180910390f35b600080600084815260200190815260200160002060000180546101f790610f1d565b905011610239576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161023090610f9a565b60405180910390fd5b600060016000838152602001908152602001600020600001805461025c90610f1d565b90501161029e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161029590611006565b60405180910390fd5b816001600083815260200190815260200160002060010154146102f6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102ed90611098565b60405180910390fd5b6001806000838152602001908152602001600020600201600082825461031c91906110e7565b925050819055506002600083815260200190815260200160002060405180606001604052803373ffffffffffffffffffffffffffffffffffffffff16815260200183815260200184815250908060018154018082558091505060019003906000526020600020906003020160009091909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002015550503373ffffffffffffffffffffffffffffffffffffffff1681837ff6ed5a0362706e33942c258dd867d1664e91a7653843a7c3459a857db97287ae600160008681526020019081526020016000206002015460405161044b9190610da2565b60405180910390a45050565b600060205280600052604060002060009150905080600001805461047a90610f1d565b80601f01602080910402602001604051908101604052809291908181526020018280546104a690610f1d565b80156104f35780601f106104c8576101008083540402835291602001916104f3565b820191906000526020600020905b8154815290600101906020018083116104d657829003601f168201915b5050505050905081565b600160205280600052604060002060009150905080600001805461052090610f1d565b80601f016020809104026020016040519081016040528092919081815260200182805461054c90610f1d565b80156105995780601f1061056e57610100808354040283529160200191610599565b820191906000526020600020905b81548152906001019060200180831161057c57829003601f168201915b5050505050908060010154908060020154905083565b600080600084815260200190815260200160002060000180546105d190610f1d565b905014610613576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161060a90611167565b60405180910390fd5b8060008084815260200190815260200160002060000190816106359190611333565b50817f52be7c4e77b4de76b7607d621492061fe13b58597e72dfb5e51ab8f6187ed141826040516106669190610abb565b60405180910390a25050565b600060016000858152602001908152602001600020600001805461069590610f1d565b9050146106d7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106ce90611451565b60405180910390fd5b600080600083815260200190815260200160002060000180546106f990610f1d565b90501161073b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161073290610f9a565b60405180910390fd5b6040518060600160405280838152602001828152602001600081525060016000858152602001908152602001600020600082015181600001908161077f9190611333565b50602082015181600101556040820151816002015590505060008082815260200190815260200160002060010183908060018154018082558091505060019003906000526020600020016000909190919091505582817f309f00963002e61accf36a0b41e620df8ff7a44b97ec5837254be10acade2fcf846040516108049190610abb565b60405180910390a3505050565b6002602052816000526040600020818154811061082d57600080fd5b9060005260206000209060030201600091509150508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154905083565b600060016000838152602001908152602001600020600201549050919050565b606060026000838152602001908152602001600020805480602002602001604051908101604052809291908181526020016000905b8282101561096957838290600052602060002090600302016040518060600160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152602001600282015481525050815260200190600101906108cd565b505050509050919050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61099b81610988565b81146109a657600080fd5b50565b6000813590506109b881610992565b92915050565b600080604083850312156109d5576109d461097e565b5b60006109e3858286016109a9565b92505060206109f4858286016109a9565b9150509250929050565b600060208284031215610a1457610a1361097e565b5b6000610a22848285016109a9565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610a65578082015181840152602081019050610a4a565b60008484015250505050565b6000601f19601f8301169050919050565b6000610a8d82610a2b565b610a978185610a36565b9350610aa7818560208601610a47565b610ab081610a71565b840191505092915050565b60006020820190508181036000830152610ad58184610a82565b905092915050565b610ae681610988565b82525050565b60006060820190508181036000830152610b068186610a82565b9050610b156020830185610add565b610b226040830184610add565b949350505050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610b6c82610a71565b810181811067ffffffffffffffff82111715610b8b57610b8a610b34565b5b80604052505050565b6000610b9e610974565b9050610baa8282610b63565b919050565b600067ffffffffffffffff821115610bca57610bc9610b34565b5b610bd382610a71565b9050602081019050919050565b82818337600083830152505050565b6000610c02610bfd84610baf565b610b94565b905082815260208101848484011115610c1e57610c1d610b2f565b5b610c29848285610be0565b509392505050565b600082601f830112610c4657610c45610b2a565b5b8135610c56848260208601610bef565b91505092915050565b60008060408385031215610c7657610c7561097e565b5b6000610c84858286016109a9565b925050602083013567ffffffffffffffff811115610ca557610ca4610983565b5b610cb185828601610c31565b9150509250929050565b600080600060608486031215610cd457610cd361097e565b5b6000610ce2868287016109a9565b935050602084013567ffffffffffffffff811115610d0357610d02610983565b5b610d0f86828701610c31565b9250506040610d20868287016109a9565b9150509250925092565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610d5582610d2a565b9050919050565b610d6581610d4a565b82525050565b6000606082019050610d806000830186610d5c565b610d8d6020830185610add565b610d9a6040830184610add565b949350505050565b6000602082019050610db76000830184610add565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b610df281610d4a565b82525050565b610e0181610988565b82525050565b606082016000820151610e1d6000850182610de9565b506020820151610e306020850182610df8565b506040820151610e436040850182610df8565b50505050565b6000610e558383610e07565b60608301905092915050565b6000602082019050919050565b6000610e7982610dbd565b610e838185610dc8565b9350610e8e83610dd9565b8060005b83811015610ebf578151610ea68882610e49565b9750610eb183610e61565b925050600181019050610e92565b5085935050505092915050565b60006020820190508181036000830152610ee68184610e6e565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610f3557607f821691505b602082108103610f4857610f47610eee565b5b50919050565b7f456c656374696f6e20646f6573206e6f74206578697374000000000000000000600082015250565b6000610f84601783610a36565b9150610f8f82610f4e565b602082019050919050565b60006020820190508181036000830152610fb381610f77565b9050919050565b7f506172747920646f6573206e6f74206578697374000000000000000000000000600082015250565b6000610ff0601483610a36565b9150610ffb82610fba565b602082019050919050565b6000602082019050818103600083015261101f81610fe3565b9050919050565b7f506172747920646f6573206e6f742062656c6f6e6720746f207468697320656c60008201527f656374696f6e0000000000000000000000000000000000000000000000000000602082015250565b6000611082602683610a36565b915061108d82611026565b604082019050919050565b600060208201905081810360008301526110b181611075565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006110f282610988565b91506110fd83610988565b9250828201905080821115611115576111146110b8565b5b92915050565b7f456c656374696f6e20616c726561647920657869737473000000000000000000600082015250565b6000611151601783610a36565b915061115c8261111b565b602082019050919050565b6000602082019050818103600083015261118081611144565b9050919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026111e97fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826111ac565b6111f386836111ac565b95508019841693508086168417925050509392505050565b6000819050919050565b600061123061122b61122684610988565b61120b565b610988565b9050919050565b6000819050919050565b61124a83611215565b61125e61125682611237565b8484546111b9565b825550505050565b600090565b611273611266565b61127e818484611241565b505050565b5b818110156112a25761129760008261126b565b600181019050611284565b5050565b601f8211156112e7576112b881611187565b6112c18461119c565b810160208510156112d0578190505b6112e46112dc8561119c565b830182611283565b50505b505050565b600082821c905092915050565b600061130a600019846008026112ec565b1980831691505092915050565b600061132383836112f9565b9150826002028217905092915050565b61133c82610a2b565b67ffffffffffffffff81111561135557611354610b34565b5b61135f8254610f1d565b61136a8282856112a6565b600060209050601f83116001811461139d576000841561138b578287015190505b6113958582611317565b8655506113fd565b601f1984166113ab86611187565b60005b828110156113d3578489015182556001820191506020850194506020810190506113ae565b868310156113f057848901516113ec601f8916826112f9565b8355505b6001600288020188555050505b505050505050565b7f506172747920616c726561647920657869737473000000000000000000000000600082015250565b600061143b601483610a36565b915061144682611405565b602082019050919050565b6000602082019050818103600083015261146a8161142e565b905091905056fea2646970667358221220f3161b0e9a4775d1e82bfeed1ab9abd6a411dac1b3822ee3773ac999235bcb9a64736f6c634300081c0033";

    public static final String FUNC_ADDPARTY = "addParty";

    public static final String FUNC_CASTVOTE = "castVote";

    public static final String FUNC_CREATEELECTION = "createElection";

    public static final String FUNC_ELECTIONS = "elections";

    public static final String FUNC_GETPARTYVOTECOUNT = "getPartyVoteCount";

    public static final String FUNC_GETVOTES = "getVotes";

    public static final String FUNC_VOTES = "votes";

    public static final Event ELECTIONCREATED_EVENT = new Event("ElectionCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event PARTYADDED_EVENT = new Event("PartyAdded",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event VOTECAST_EVENT = new Event("VoteCast",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected VotingSystem(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VotingSystem(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VotingSystem(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VotingSystem(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ElectionCreatedEventResponse> getElectionCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ELECTIONCREATED_EVENT, log);
            if (eventValues != null) {
                valueList.add(eventValues);
            }
        }
        ArrayList<ElectionCreatedEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ElectionCreatedEventResponse typedResponse = new ElectionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }


    public static ElectionCreatedEventResponse getElectionCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ELECTIONCREATED_EVENT, log);
        ElectionCreatedEventResponse typedResponse = new ElectionCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ElectionCreatedEventResponse> electionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getElectionCreatedEventFromLog(log));
    }

    public Flowable<ElectionCreatedEventResponse> electionCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ELECTIONCREATED_EVENT));
        return electionCreatedEventFlowable(filter);
    }

    public static List<PartyAddedEventResponse> getPartyAddedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PARTYADDED_EVENT, log);
            if (eventValues != null) {
                valueList.add(eventValues);
            }
        }
        ArrayList<PartyAddedEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PartyAddedEventResponse typedResponse = new PartyAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PartyAddedEventResponse getPartyAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PARTYADDED_EVENT, log);
        PartyAddedEventResponse typedResponse = new PartyAddedEventResponse();
        typedResponse.log = log;
        typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PartyAddedEventResponse> partyAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPartyAddedEventFromLog(log));
    }

    public Flowable<PartyAddedEventResponse> partyAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PARTYADDED_EVENT));
        return partyAddedEventFlowable(filter);
    }

    public static List<VoteCastEventResponse> getVoteCastEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTECAST_EVENT, log);
            if (eventValues != null) {
                valueList.add(eventValues);
            }
        }
        ArrayList<VoteCastEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoteCastEventResponse typedResponse = new VoteCastEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.voter = ((Address) eventValues.getIndexedValues().get(2)).getValue();
            typedResponse.voteCount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VoteCastEventResponse getVoteCastEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTECAST_EVENT, log);
        VoteCastEventResponse typedResponse = new VoteCastEventResponse();
        typedResponse.log = log;
        typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.voter = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.voteCount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<VoteCastEventResponse> voteCastEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVoteCastEventFromLog(log));
    }

    public Flowable<VoteCastEventResponse> voteCastEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTECAST_EVENT));
        return voteCastEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addParty(BigInteger partyId, String name,
            BigInteger electionId) {
        final Function function = new Function(
                FUNC_ADDPARTY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(partyId),
                new org.web3j.abi.datatypes.Utf8String(name),
                new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> castVote(BigInteger electionId,
            BigInteger partyId) {
        final Function function = new Function(
                FUNC_CASTVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId),
                new org.web3j.abi.datatypes.generated.Uint256(partyId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createElection(BigInteger electionId,
            String title) {
        final Function function = new Function(
                FUNC_CREATEELECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId),
                new org.web3j.abi.datatypes.Utf8String(title)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> elections(BigInteger param0) {
        final Function function = new Function(FUNC_ELECTIONS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPartyVoteCount(BigInteger partyId) {
        final Function function = new Function(FUNC_GETPARTYVOTECOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(partyId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getVotes(BigInteger electionId) {
        final Function function = new Function(FUNC_GETVOTES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Vote>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>> votes(BigInteger param0,
            BigInteger param1) {
        final Function function = new Function(FUNC_VOTES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0),
                new org.web3j.abi.datatypes.generated.Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static VotingSystem load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingSystem(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VotingSystem load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingSystem(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VotingSystem load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new VotingSystem(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VotingSystem load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VotingSystem(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class Vote extends StaticStruct {
        public String voter;

        public BigInteger partyId;

        public BigInteger electionId;

        public Vote(String voter, BigInteger partyId, BigInteger electionId) {
            super(new org.web3j.abi.datatypes.Address(160, voter),
                    new org.web3j.abi.datatypes.generated.Uint256(partyId),
                    new org.web3j.abi.datatypes.generated.Uint256(electionId));
            this.voter = voter;
            this.partyId = partyId;
            this.electionId = electionId;
        }

        public Vote(Address voter, Uint256 partyId, Uint256 electionId) {
            super(voter, partyId, electionId);
            this.voter = voter.getValue();
            this.partyId = partyId.getValue();
            this.electionId = electionId.getValue();
        }
    }

    public static class ElectionCreatedEventResponse extends BaseEventResponse {
        public BigInteger electionId;

        public String title;
    }

    public static class PartyAddedEventResponse extends BaseEventResponse {
        public BigInteger electionId;

        public BigInteger partyId;

        public String name;
    }

    public static class VoteCastEventResponse extends BaseEventResponse {
        public BigInteger electionId;

        public BigInteger partyId;

        public String voter;

        public BigInteger voteCount;
    }
}
