package com.example.e_voting_system.Security;

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
public class DeprecatedVotingSystem extends Contract {
    public static final String BINARY = "0x6080604052348015600f57600080fd5b506124778061001f6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c8063947582181161007157806394758218146101b4578063b6c8ecb2146101e7578063c3069bb214610203578063c6e36a3214610233578063df8ecf5414610268578063ff98109914610298576100b4565b8063150988c3146100b95780635e6fef01146100e957806370651a1814610119578063748060971461014c5780637da09d941461017c5780638779aaf214610198575b600080fd5b6100d360048036038101906100ce91906115f8565b6102c8565b6040516100e09190611634565b60405180910390f35b61010360048036038101906100fe91906115f8565b6102ea565b60405161011091906116df565b60405180910390f35b610133600480360381019061012e91906115f8565b610390565b6040516101439493929190611701565b60405180910390f35b61016660048036038101906101619190611754565b6104d0565b6040516101739190611946565b60405180910390f35b61019660048036038101906101919190611a9d565b610852565b005b6101b260048036038101906101ad9190611af9565b610915565b005b6101ce60048036038101906101c991906115f8565b610cee565b6040516101de9493929190611701565b60405180910390f35b61020160048036038101906101fc9190611b7c565b610e46565b005b61021d600480360381019061021891906115f8565b6110b4565b60405161022a9190611c9a565b60405180910390f35b61024d60048036038101906102489190611754565b611121565b60405161025f96959493929190611cbc565b60405180910390f35b610282600480360381019061027d91906115f8565b611312565b60405161028f9190611634565b60405180910390f35b6102b260048036038101906102ad91906115f8565b611332565b6040516102bf9190611946565b60405180910390f35b6000806000838152602001908152602001600020600101805490509050919050565b600060205280600052604060002060009150905080600001805461030d90611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461033990611d61565b80156103865780601f1061035b57610100808354040283529160200191610386565b820191906000526020600020905b81548152906001019060200180831161036957829003601f168201915b5050505050905081565b60016020528060005260406000206000915090508060000180546103b390611d61565b80601f01602080910402602001604051908101604052809291908181526020018280546103df90611d61565b801561042c5780601f106104015761010080835404028352916020019161042c565b820191906000526020600020905b81548152906001019060200180831161040f57829003601f168201915b50505050509080600101549080600201805461044790611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461047390611d61565b80156104c05780601f10610495576101008083540402835291602001916104c0565b820191906000526020600020905b8154815290600101906020018083116104a357829003601f168201915b5050505050908060030154905084565b6060600060026000858152602001908152602001600020805490509050600080600090505b82811015610556578460026000888152602001908152602001600020828154811061052357610522611d92565b5b9060005260206000209060060201600301540361054957818061054590611df0565b9250505b80806001019150506104f5565b5060008167ffffffffffffffff81111561057357610572611972565b5b6040519080825280602002602001820160405280156105ac57816020015b610599611578565b8152602001906001900390816105915790505b5090506000805b848110156108445786600260008a815260200190815260200160002082815481106105e1576105e0611d92565b5b906000526020600020906006020160030154036108375760026000898152602001908152602001600020818154811061061d5761061c611d92565b5b90600052602060002090600602016040518060c0016040529081600082015481526020016001820154815260200160028201805461065a90611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461068690611d61565b80156106d35780601f106106a8576101008083540402835291602001916106d3565b820191906000526020600020905b8154815290600101906020018083116106b657829003601f168201915b50505050508152602001600382015481526020016004820180546106f690611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461072290611d61565b801561076f5780601f106107445761010080835404028352916020019161076f565b820191906000526020600020905b81548152906001019060200180831161075257829003601f168201915b5050505050815260200160058201805461078890611d61565b80601f01602080910402602001604051908101604052809291908181526020018280546107b490611d61565b80156108015780601f106107d657610100808354040283529160200191610801565b820191906000526020600020905b8154815290600101906020018083116107e457829003601f168201915b50505050508152505083838151811061081d5761081c611d92565b5b6020026020010181905250818061083390611df0565b9250505b80806001019150506105b3565b508194505050505092915050565b6000806000848152602001908152602001600020600001805461087490611d61565b9050146108b6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108ad90611e84565b60405180910390fd5b8060008084815260200190815260200160002060000190816108d89190612050565b50817f52be7c4e77b4de76b7607d621492061fe13b58597e72dfb5e51ab8f6187ed1418260405161090991906116df565b60405180910390a25050565b6000806000868152602001908152602001600020600001805461093790611d61565b905011610979576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109709061216e565b60405180910390fd5b600060016000858152602001908152602001600020600001805461099c90611d61565b9050116109de576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109d5906121da565b60405180910390fd5b83600160008581526020019081526020016000206001015414610a36576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a2d9061226c565b60405180910390fd5b60018060008581526020019081526020016000206003016000828254610a5c919061228c565b92505081905550600260008581526020019081526020016000206040518060c001604052808481526020018681526020016000808881526020019081526020016000206000018054610aad90611d61565b80601f0160208091040260200160405190810160405280929190818152602001828054610ad990611d61565b8015610b265780601f10610afb57610100808354040283529160200191610b26565b820191906000526020600020905b815481529060010190602001808311610b0957829003601f168201915b50505050508152602001858152602001600160008781526020019081526020016000206000018054610b5790611d61565b80601f0160208091040260200160405190810160405280929190818152602001828054610b8390611d61565b8015610bd05780601f10610ba557610100808354040283529160200191610bd0565b820191906000526020600020905b815481529060010190602001808311610bb357829003601f168201915b5050505050815260200183815250908060018154018082558091505060019003906000526020600020906006020160009091909190915060008201518160000155602082015181600101556040820151816002019081610c309190612050565b50606082015181600301556080820151816004019081610c509190612050565b5060a0820151816005019081610c669190612050565b5050508183857f0169c9d1c45acda66ffaa50d6f81f1c35836092cd7b9d14a7e596f2222a201516000808981526020019081526020016000206000016001600089815260200190815260200160002060000186600160008b815260200190815260200160002060030154604051610ce09493929190612344565b60405180910390a450505050565b606060006060600080600160008781526020019081526020016000209050806000018160010154826002018360030154838054610d2a90611d61565b80601f0160208091040260200160405190810160405280929190818152602001828054610d5690611d61565b8015610da35780601f10610d7857610100808354040283529160200191610da3565b820191906000526020600020905b815481529060010190602001808311610d8657829003601f168201915b50505050509350818054610db690611d61565b80601f0160208091040260200160405190810160405280929190818152602001828054610de290611d61565b8015610e2f5780601f10610e0457610100808354040283529160200191610e2f565b820191906000526020600020905b815481529060010190602001808311610e1257829003601f168201915b505050505091509450945094509450509193509193565b6000600160008581526020019081526020016000206000018054610e6990611d61565b905014610eab576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ea2906123ea565b60405180910390fd5b60008060008381526020019081526020016000206000018054610ecd90611d61565b905011610f0f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f069061216e565b60405180910390fd5b60405180608001604052808381526020018281526020016000808481526020019081526020016000206000018054610f4690611d61565b80601f0160208091040260200160405190810160405280929190818152602001828054610f7290611d61565b8015610fbf5780601f10610f9457610100808354040283529160200191610fbf565b820191906000526020600020905b815481529060010190602001808311610fa257829003601f168201915b505050505081526020016000815250600160008581526020019081526020016000206000820151816000019081610ff69190612050565b506020820151816001015560408201518160020190816110169190612050565b506060820151816003015590505060008082815260200190815260200160002060010183908060018154018082558091505060019003906000526020600020016000909190919091505582817fcae1857fb978eeb01d5b4733660d52ca2c8d8e2ce92e75c6c56cae86d097c301846000808681526020019081526020016000206000016040516110a792919061240a565b60405180910390a3505050565b606060008083815260200190815260200160002060010180548060200260200160405190810160405280929190818152602001828054801561111557602002820191906000526020600020905b815481526020019060010190808311611101575b50505050509050919050565b6002602052816000526040600020818154811061113d57600080fd5b90600052602060002090600602016000915091505080600001549080600101549080600201805461116d90611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461119990611d61565b80156111e65780601f106111bb576101008083540402835291602001916111e6565b820191906000526020600020905b8154815290600101906020018083116111c957829003601f168201915b50505050509080600301549080600401805461120190611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461122d90611d61565b801561127a5780601f1061124f5761010080835404028352916020019161127a565b820191906000526020600020905b81548152906001019060200180831161125d57829003601f168201915b50505050509080600501805461128f90611d61565b80601f01602080910402602001604051908101604052809291908181526020018280546112bb90611d61565b80156113085780601f106112dd57610100808354040283529160200191611308565b820191906000526020600020905b8154815290600101906020018083116112eb57829003601f168201915b5050505050905086565b600060016000838152602001908152602001600020600301549050919050565b606060026000838152602001908152602001600020805480602002602001604051908101604052809291908181526020016000905b8282101561156d57838290600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820180546113ae90611d61565b80601f01602080910402602001604051908101604052809291908181526020018280546113da90611d61565b80156114275780601f106113fc57610100808354040283529160200191611427565b820191906000526020600020905b81548152906001019060200180831161140a57829003601f168201915b505050505081526020016003820154815260200160048201805461144a90611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461147690611d61565b80156114c35780601f10611498576101008083540402835291602001916114c3565b820191906000526020600020905b8154815290600101906020018083116114a657829003601f168201915b505050505081526020016005820180546114dc90611d61565b80601f016020809104026020016040519081016040528092919081815260200182805461150890611d61565b80156115555780601f1061152a57610100808354040283529160200191611555565b820191906000526020600020905b81548152906001019060200180831161153857829003601f168201915b50505050508152505081526020019060010190611367565b505050509050919050565b6040518060c001604052806000815260200160008152602001606081526020016000815260200160608152602001606081525090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b6115d5816115c2565b81146115e057600080fd5b50565b6000813590506115f2816115cc565b92915050565b60006020828403121561160e5761160d6115b8565b5b600061161c848285016115e3565b91505092915050565b61162e816115c2565b82525050565b60006020820190506116496000830184611625565b92915050565b600081519050919050565b600082825260208201905092915050565b60005b8381101561168957808201518184015260208101905061166e565b60008484015250505050565b6000601f19601f8301169050919050565b60006116b18261164f565b6116bb818561165a565b93506116cb81856020860161166b565b6116d481611695565b840191505092915050565b600060208201905081810360008301526116f981846116a6565b905092915050565b6000608082019050818103600083015261171b81876116a6565b905061172a6020830186611625565b818103604083015261173c81856116a6565b905061174b6060830184611625565b95945050505050565b6000806040838503121561176b5761176a6115b8565b5b6000611779858286016115e3565b925050602061178a858286016115e3565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6117c9816115c2565b82525050565b600082825260208201905092915050565b60006117eb8261164f565b6117f581856117cf565b935061180581856020860161166b565b61180e81611695565b840191505092915050565b600060c08301600083015161183160008601826117c0565b50602083015161184460208601826117c0565b506040830151848203604086015261185c82826117e0565b915050606083015161187160608601826117c0565b506080830151848203608086015261188982826117e0565b91505060a083015184820360a08601526118a382826117e0565b9150508091505092915050565b60006118bc8383611819565b905092915050565b6000602082019050919050565b60006118dc82611794565b6118e6818561179f565b9350836020820285016118f8856117b0565b8060005b85811015611934578484038952815161191585826118b0565b9450611920836118c4565b925060208a019950506001810190506118fc565b50829750879550505050505092915050565b6000602082019050818103600083015261196081846118d1565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6119aa82611695565b810181811067ffffffffffffffff821117156119c9576119c8611972565b5b80604052505050565b60006119dc6115ae565b90506119e882826119a1565b919050565b600067ffffffffffffffff821115611a0857611a07611972565b5b611a1182611695565b9050602081019050919050565b82818337600083830152505050565b6000611a40611a3b846119ed565b6119d2565b905082815260208101848484011115611a5c57611a5b61196d565b5b611a67848285611a1e565b509392505050565b600082601f830112611a8457611a83611968565b5b8135611a94848260208601611a2d565b91505092915050565b60008060408385031215611ab457611ab36115b8565b5b6000611ac2858286016115e3565b925050602083013567ffffffffffffffff811115611ae357611ae26115bd565b5b611aef85828601611a6f565b9150509250929050565b60008060008060808587031215611b1357611b126115b8565b5b6000611b21878288016115e3565b9450506020611b32878288016115e3565b9350506040611b43878288016115e3565b925050606085013567ffffffffffffffff811115611b6457611b636115bd565b5b611b7087828801611a6f565b91505092959194509250565b600080600060608486031215611b9557611b946115b8565b5b6000611ba3868287016115e3565b935050602084013567ffffffffffffffff811115611bc457611bc36115bd565b5b611bd086828701611a6f565b9250506040611be1868287016115e3565b9150509250925092565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000611c2383836117c0565b60208301905092915050565b6000602082019050919050565b6000611c4782611beb565b611c518185611bf6565b9350611c5c83611c07565b8060005b83811015611c8d578151611c748882611c17565b9750611c7f83611c2f565b925050600181019050611c60565b5085935050505092915050565b60006020820190508181036000830152611cb48184611c3c565b905092915050565b600060c082019050611cd16000830189611625565b611cde6020830188611625565b8181036040830152611cf081876116a6565b9050611cff6060830186611625565b8181036080830152611d1181856116a6565b905081810360a0830152611d2581846116a6565b9050979650505050505050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611d7957607f821691505b602082108103611d8c57611d8b611d32565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611dfb826115c2565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611e2d57611e2c611dc1565b5b600182019050919050565b7f456c656374696f6e20616c726561647920657869737473000000000000000000600082015250565b6000611e6e60178361165a565b9150611e7982611e38565b602082019050919050565b60006020820190508181036000830152611e9d81611e61565b9050919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302611f067fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611ec9565b611f108683611ec9565b95508019841693508086168417925050509392505050565b6000819050919050565b6000611f4d611f48611f43846115c2565b611f28565b6115c2565b9050919050565b6000819050919050565b611f6783611f32565b611f7b611f7382611f54565b848454611ed6565b825550505050565b600090565b611f90611f83565b611f9b818484611f5e565b505050565b5b81811015611fbf57611fb4600082611f88565b600181019050611fa1565b5050565b601f82111561200457611fd581611ea4565b611fde84611eb9565b81016020851015611fed578190505b612001611ff985611eb9565b830182611fa0565b50505b505050565b600082821c905092915050565b600061202760001984600802612009565b1980831691505092915050565b60006120408383612016565b9150826002028217905092915050565b6120598261164f565b67ffffffffffffffff81111561207257612071611972565b5b61207c8254611d61565b612087828285611fc3565b600060209050601f8311600181146120ba57600084156120a8578287015190505b6120b28582612034565b86555061211a565b601f1984166120c886611ea4565b60005b828110156120f0578489015182556001820191506020850194506020810190506120cb565b8683101561210d5784890151612109601f891682612016565b8355505b6001600288020188555050505b505050505050565b7f456c656374696f6e20646f6573206e6f74206578697374000000000000000000600082015250565b600061215860178361165a565b915061216382612122565b602082019050919050565b600060208201905081810360008301526121878161214b565b9050919050565b7f506172747920646f6573206e6f74206578697374000000000000000000000000600082015250565b60006121c460148361165a565b91506121cf8261218e565b602082019050919050565b600060208201905081810360008301526121f3816121b7565b9050919050565b7f506172747920646f6573206e6f742062656c6f6e6720746f207468697320656c60008201527f656374696f6e0000000000000000000000000000000000000000000000000000602082015250565b600061225660268361165a565b9150612261826121fa565b604082019050919050565b6000602082019050818103600083015261228581612249565b9050919050565b6000612297826115c2565b91506122a2836115c2565b92508282019050808211156122ba576122b9611dc1565b5b92915050565b600081546122cd81611d61565b6122d7818661165a565b945060018216600081146122f257600181146123085761233b565b60ff19831686528115156020028601935061233b565b61231185611ea4565b60005b8381101561233357815481890152600182019150602081019050612314565b808801955050505b50505092915050565b6000608082019050818103600083015261235e81876122c0565b9050818103602083015261237281866122c0565b9050818103604083015261238681856116a6565b90506123956060830184611625565b95945050505050565b7f506172747920616c726561647920657869737473000000000000000000000000600082015250565b60006123d460148361165a565b91506123df8261239e565b602082019050919050565b60006020820190508181036000830152612403816123c7565b9050919050565b6000604082019050818103600083015261242481856116a6565b9050818103602083015261243881846122c0565b9050939250505056fea2646970667358221220eac5d01fc74fc989e1b68cde8352c2e0b87fc3f540478738a7485b08a41f2ec564736f6c634300081c0033";

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
                    new TypeReference<Utf8String>() {},
                    new TypeReference<Uint256>() {}
            )
    );

    @Deprecated
    protected DeprecatedVotingSystem(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DeprecatedVotingSystem(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider
    ) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DeprecatedVotingSystem(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DeprecatedVotingSystem(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider
    ) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    // ---------------------------------------------------------------------------------------------
    // 1. getElectionCreatedEvents
    // ---------------------------------------------------------------------------------------------
    public static List<ElectionCreatedEventResponse> getElectionCreatedEvents(TransactionReceipt transactionReceipt) {
        // We manually iterate over the logs in the transactionReceipt
        List<Contract.EventValuesWithLog> valueList = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            // Attempt to extract EventValues from each log
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
            DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock
    ) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ELECTIONCREATED_EVENT));
        return electionCreatedEventFlowable(filter);
    }

    // ---------------------------------------------------------------------------------------------
    // 2. getPartyAddedEvents
    // ---------------------------------------------------------------------------------------------
    public static List<PartyAddedEventResponse> getPartyAddedEvents(TransactionReceipt transactionReceipt) {
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
            typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(1).getValue();
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
        typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PartyAddedEventResponse> partyAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPartyAddedEventFromLog(log));
    }

    public Flowable<PartyAddedEventResponse> partyAddedEventFlowable(
            DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock
    ) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PARTYADDED_EVENT));
        return partyAddedEventFlowable(filter);
    }

    // ---------------------------------------------------------------------------------------------
    // 3. getVoteCastEvents
    // ---------------------------------------------------------------------------------------------
    public static List<VoteCastEventResponse> getVoteCastEvents(TransactionReceipt transactionReceipt) {
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
            typedResponse.voter = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.partyName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.characterName = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.voteCount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
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
        typedResponse.voter = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.electionTitle = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.partyName = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.characterName = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.voteCount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
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
    // 4. Contract Functions (Transactions)
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

    // ---------------------------------------------------------------------------------------------
    // 5. Contract Functions (Reads)
    // ---------------------------------------------------------------------------------------------
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

    @SuppressWarnings("unchecked")
    public RemoteFunctionCall<List> getPartyIdsForElection(BigInteger electionId) {
        final Function function = new Function(
                FUNC_GETPARTYIDSFORELECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {})
        );
        return new RemoteFunctionCall<>(function, new Callable<List>() {
            @Override
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

    @SuppressWarnings("unchecked")
    public RemoteFunctionCall<List> getVotes(BigInteger electionId) {
        final Function function = new Function(
                FUNC_GETVOTES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(electionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Vote>>() {})
        );
        return new RemoteFunctionCall<>(function, new Callable<List>() {
            @Override
            public List call() throws Exception {
                List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                return convertToNative(result);
            }
        });
    }

    @SuppressWarnings("unchecked")
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
            public List call() throws Exception {
                List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                return convertToNative(result);
            }
        });
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, String, BigInteger>> parties(BigInteger param0) {
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

    // ---------------------------------------------------------------------------------------------
    // 6. Load Methods
    // ---------------------------------------------------------------------------------------------
    @Deprecated
    public static DeprecatedVotingSystem load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        return new DeprecatedVotingSystem(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DeprecatedVotingSystem load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit
    ) {
        return new DeprecatedVotingSystem(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DeprecatedVotingSystem load(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider
    ) {
        return new DeprecatedVotingSystem(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DeprecatedVotingSystem load(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider
    ) {
        return new DeprecatedVotingSystem(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    // ---------------------------------------------------------------------------------------------
    // 7. Inner Classes (Structures for 'Vote', Event Responses)
    // ---------------------------------------------------------------------------------------------
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
        public BigInteger voteCount;
    }
}
