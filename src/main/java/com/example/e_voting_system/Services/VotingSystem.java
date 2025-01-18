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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
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
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple6;
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
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDPARTY = "addParty";
    public static final String FUNC_CASTVOTE = "castVote";
    public static final String FUNC_CREATEELECTION = "createElection";
    public static final String FUNC_ELECTIONS = "elections";
    public static final String FUNC_GETPARTY = "getParty";
    public static final String FUNC_GETPARTYCOUNT = "getPartyCount";
    public static final String FUNC_GETPARTYIDSFORELECTION = "getPartyIdsForElection";
    public static final String FUNC_GETPARTYVOTECOUNT = "getPartyVoteCount";
    public static final String FUNC_GETVOTES = "getVotes";
    public static final String FUNC_GETVOTESFORPARTY = "getVotesForParty";
    public static final String FUNC_PARTIES = "parties";
    public static final String FUNC_VOTES = "votes";

    public static final Event ELECTIONCREATED_EVENT = new Event("ElectionCreated",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Utf8String>() {}
            )
    );

    public static final Event PARTYADDED_EVENT = new Event("PartyAdded",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {}
            )
    );

    public static final Event VOTECAST_EVENT = new Event("VoteCast",
            Arrays.<TypeReference<?>>asList(
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Uint256>(true) {},
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Utf8String>() {}
            )
    );

    @Deprecated
    protected VotingSystem(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VotingSystem(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider
    ) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VotingSystem(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VotingSystem(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider
    ) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    // ---------------------------------------------------------------------------------------------
    // 1. Corrected getElectionCreatedEvents
    // ---------------------------------------------------------------------------------------------
    public static List<ElectionCreatedEventResponse> getElectionCreatedEvents(TransactionReceipt transactionReceipt) {
        // Instead of passing the entire transactionReceipt directly,
        // we iterate through each Log and compare the event signature.
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            if (log.getTopics().get(0).equals(EventEncoder.encode(ELECTIONCREATED_EVENT))) {
                Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ELECTIONCREATED_EVENT, log);
                if (eventValues != null) {
                    valueList.add(eventValues);
                }
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
        if (!log.getTopics().isEmpty()
                && log.getTopics().get(0).equals(EventEncoder.encode(ELECTIONCREATED_EVENT))) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ELECTIONCREATED_EVENT, log);
            ElectionCreatedEventResponse typedResponse = new ElectionCreatedEventResponse();
            typedResponse.log = log;
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.title = (String) eventValues.getNonIndexedValues().get(0).getValue();
            return typedResponse;
        }
        return null;
    }

    public Flowable<ElectionCreatedEventResponse> electionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getElectionCreatedEventFromLog(log));
    }

    public Flowable<ElectionCreatedEventResponse> electionCreatedEventFlowable(
            DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock
    ) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ELECTIONCREATED_EVENT));
        return electionCreatedEventFlowable(filter);
    }

    // ---------------------------------------------------------------------------------------------
    // 2. Corrected getPartyAddedEvents
    // ---------------------------------------------------------------------------------------------
    public static List<PartyAddedEventResponse> getPartyAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            if (log.getTopics().get(0).equals(EventEncoder.encode(PARTYADDED_EVENT))) {
                Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PARTYADDED_EVENT, log);
                if (eventValues != null) {
                    valueList.add(eventValues);
                }
            }
        }
        ArrayList<PartyAddedEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PartyAddedEventResponse typedResponse = new PartyAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PartyAddedEventResponse getPartyAddedEventFromLog(Log log) {
        if (!log.getTopics().isEmpty()
                && log.getTopics().get(0).equals(EventEncoder.encode(PARTYADDED_EVENT))) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PARTYADDED_EVENT, log);
            PartyAddedEventResponse typedResponse = new PartyAddedEventResponse();
            typedResponse.log = log;
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(1).getValue();
            return typedResponse;
        }
        return null;
    }

    public Flowable<PartyAddedEventResponse> partyAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPartyAddedEventFromLog(log));
    }

    public Flowable<PartyAddedEventResponse> partyAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock
    ) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PARTYADDED_EVENT));
        return partyAddedEventFlowable(filter);
    }

    // ---------------------------------------------------------------------------------------------
    // 3. Corrected getVoteCastEvents
    // ---------------------------------------------------------------------------------------------
    public static List<VoteCastEventResponse> getVoteCastEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            if (log.getTopics().get(0).equals(EventEncoder.encode(VOTECAST_EVENT))) {
                Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTECAST_EVENT, log);
                if (eventValues != null) {
                    valueList.add(eventValues);
                }
            }
        }
        ArrayList<VoteCastEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoteCastEventResponse typedResponse = new VoteCastEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.voter = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.partyName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.characterName = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VoteCastEventResponse getVoteCastEventFromLog(Log log) {
        if (!log.getTopics().isEmpty()
                && log.getTopics().get(0).equals(EventEncoder.encode(VOTECAST_EVENT))) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTECAST_EVENT, log);
            VoteCastEventResponse typedResponse = new VoteCastEventResponse();
            typedResponse.log = log;
            typedResponse.electionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.partyId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.voter = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.partyName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.characterName = (String) eventValues.getNonIndexedValues().get(2).getValue();
            return typedResponse;
        }
        return null;
    }

    public Flowable<VoteCastEventResponse> voteCastEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVoteCastEventFromLog(log));
    }

    public Flowable<VoteCastEventResponse> voteCastEventFlowable(
            DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock
    ) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTECAST_EVENT));
        return voteCastEventFlowable(filter);
    }

    // ---------------------------------------------------------------------------------------------
    // 4. Contract Functions
    // ---------------------------------------------------------------------------------------------
    public RemoteFunctionCall<TransactionReceipt> addParty(
            BigInteger partyId,
            String name,
            BigInteger electionId
    ) {
        final Function function = new Function(
                FUNC_ADDPARTY,
                Arrays.<Type>asList(
                        new org.web3j.abi.datatypes.generated.Uint256(partyId),
                        new org.web3j.abi.datatypes.Utf8String(name),
                        new org.web3j.abi.datatypes.generated.Uint256(electionId)
                ),
                Collections.<TypeReference<?>>emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> castVote(
            BigInteger electionId,
            BigInteger partyId,
            BigInteger voter,
            String characterName
    ) {
        final Function function = new Function(
                FUNC_CASTVOTE,
                Arrays.<Type>asList(
                        new org.web3j.abi.datatypes.generated.Uint256(electionId),
                        new org.web3j.abi.datatypes.generated.Uint256(partyId),
                        new org.web3j.abi.datatypes.generated.Uint256(voter),
                        new org.web3j.abi.datatypes.Utf8String(characterName)
                ),
                Collections.<TypeReference<?>>emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createElection(
            BigInteger electionId,
            String title
    ) {
        final Function function = new Function(
                FUNC_CREATEELECTION,
                Arrays.<Type>asList(
                        new org.web3j.abi.datatypes.generated.Uint256(electionId),
                        new org.web3j.abi.datatypes.Utf8String(title)
                ),
                Collections.<TypeReference<?>>emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> elections(BigInteger param0) {
        final Function function = new Function(
                FUNC_ELECTIONS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {})
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, String, BigInteger>> getParty(
            BigInteger partyId
    ) {
        final Function function = new Function(
                FUNC_GETPARTY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(partyId)),
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Uint256>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Uint256>() {}
                )
        );
        return new RemoteFunctionCall<>(function, new Callable<Tuple4<String, BigInteger, String, BigInteger>>() {
            @Override
            public Tuple4<String, BigInteger, String, BigInteger> call() throws Exception {
                List<Type> results = executeCallMultipleValueReturn(function);
                return new Tuple4<>(
                        (String) results.get(0).getValue(),
                        (BigInteger) results.get(1).getValue(),
                        (String) results.get(2).getValue(),
                        (BigInteger) results.get(3).getValue()
                );
            }
        });
    }

    public RemoteFunctionCall<BigInteger> getPartyCount(BigInteger electionId) {
        final Function function = new Function(
                FUNC_GETPARTYCOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getPartyIdsForElection(BigInteger electionId) {
        final Function function = new Function(
                FUNC_GETPARTYIDSFORELECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {})
        );
        return new RemoteFunctionCall<>(function, new Callable<List>() {
            @Override
            @SuppressWarnings("unchecked")
            public List call() throws Exception {
                List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                return convertToNative(result);
            }
        });
    }

    public RemoteFunctionCall<BigInteger> getPartyVoteCount(BigInteger partyId) {
        final Function function = new Function(
                FUNC_GETPARTYVOTECOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(partyId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getVotes(BigInteger electionId) {
        final Function function = new Function(
                FUNC_GETVOTES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Vote>>() {})
        );
        return new RemoteFunctionCall<>(function, new Callable<List>() {
            @Override
            @SuppressWarnings("unchecked")
            public List call() throws Exception {
                List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                return convertToNative(result);
            }
        });
    }

    public RemoteFunctionCall<List> getVotesForParty(BigInteger electionId, BigInteger partyId) {
        final Function function = new Function(
                FUNC_GETVOTESFORPARTY,
                Arrays.<Type>asList(
                        new org.web3j.abi.datatypes.generated.Uint256(electionId),
                        new org.web3j.abi.datatypes.generated.Uint256(partyId)
                ),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Vote>>() {})
        );
        return new RemoteFunctionCall<>(function, new Callable<List>() {
            @Override
            @SuppressWarnings("unchecked")
            public List call() throws Exception {
                List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                return convertToNative(result);
            }
        });
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, String, BigInteger>> parties(
            BigInteger param0
    ) {
        final Function function = new Function(
                FUNC_PARTIES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Uint256>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Uint256>() {}
                )
        );
        return new RemoteFunctionCall<>(function, new Callable<Tuple4<String, BigInteger, String, BigInteger>>() {
            @Override
            public Tuple4<String, BigInteger, String, BigInteger> call() throws Exception {
                List<Type> results = executeCallMultipleValueReturn(function);
                return new Tuple4<>(
                        (String) results.get(0).getValue(),
                        (BigInteger) results.get(1).getValue(),
                        (String) results.get(2).getValue(),
                        (BigInteger) results.get(3).getValue()
                );
            }
        });
    }

    public RemoteFunctionCall<Tuple6<BigInteger, BigInteger, String, BigInteger, String, String>> votes(
            BigInteger param0,
            BigInteger param1
    ) {
        final Function function = new Function(
                FUNC_VOTES,
                Arrays.<Type>asList(
                        new org.web3j.abi.datatypes.generated.Uint256(param0),
                        new org.web3j.abi.datatypes.generated.Uint256(param1)
                ),
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Uint256>() {},
                        new TypeReference<Uint256>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Uint256>() {},
                        new TypeReference<Utf8String>() {},
                        new TypeReference<Utf8String>() {}
                )
        );
        return new RemoteFunctionCall<>(function, new Callable<Tuple6<BigInteger, BigInteger, String, BigInteger, String, String>>() {
            @Override
            public Tuple6<BigInteger, BigInteger, String, BigInteger, String, String> call() throws Exception {
                List<Type> results = executeCallMultipleValueReturn(function);
                return new Tuple6<>(
                        (BigInteger) results.get(0).getValue(),
                        (BigInteger) results.get(1).getValue(),
                        (String) results.get(2).getValue(),
                        (BigInteger) results.get(3).getValue(),
                        (String) results.get(4).getValue(),
                        (String) results.get(5).getValue()
                );
            }
        });
    }

    @Deprecated
    public static VotingSystem load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        return new VotingSystem(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VotingSystem load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        return new VotingSystem(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VotingSystem load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider
    ) {
        return new VotingSystem(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VotingSystem load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider
    ) {
        return new VotingSystem(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class Vote extends DynamicStruct {
        public BigInteger voter;
        public BigInteger electionId;
        public String electionTitle;
        public BigInteger partyId;
        public String partyName;
        public String characterName;

        public Vote(
                BigInteger voter,
                BigInteger electionId,
                String electionTitle,
                BigInteger partyId,
                String partyName,
                String characterName
        ) {
            super(
                    new org.web3j.abi.datatypes.generated.Uint256(voter),
                    new org.web3j.abi.datatypes.generated.Uint256(electionId),
                    new org.web3j.abi.datatypes.Utf8String(electionTitle),
                    new org.web3j.abi.datatypes.generated.Uint256(partyId),
                    new org.web3j.abi.datatypes.Utf8String(partyName),
                    new org.web3j.abi.datatypes.Utf8String(characterName)
            );
            this.voter = voter;
            this.electionId = electionId;
            this.electionTitle = electionTitle;
            this.partyId = partyId;
            this.partyName = partyName;
            this.characterName = characterName;
        }

        public Vote(
                Uint256 voter,
                Uint256 electionId,
                Utf8String electionTitle,
                Uint256 partyId,
                Utf8String partyName,
                Utf8String characterName
        ) {
            super(voter, electionId, electionTitle, partyId, partyName, characterName);
            this.voter = voter.getValue();
            this.electionId = electionId.getValue();
            this.electionTitle = electionTitle.getValue();
            this.partyId = partyId.getValue();
            this.partyName = partyName.getValue();
            this.characterName = characterName.getValue();
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
        public String electionTitle;
    }

    public static class VoteCastEventResponse extends BaseEventResponse {
        public BigInteger electionId;
        public BigInteger partyId;
        public BigInteger voter;
        public String electionTitle;
        public String partyName;
        public String characterName;
    }
}
