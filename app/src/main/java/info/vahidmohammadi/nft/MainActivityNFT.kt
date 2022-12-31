package info.vahidmohammadi.nft

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider


class MainActivityNFT : AppCompatActivity() {

    // Wallet Private Key
    private val PRIVATE_KEY =
        "your wallet private key"

    // Alchemy API Https url
    private val ALCHEMY_API = "https://eth-goerli.g.alchemy.com/v2/***"

    // Web3j
    private lateinit var web3j: Web3j

    // CoroutineScope to do network related
    private val scope = CoroutineScope(Dispatchers.IO)

    // Token ID
    val tokenId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nft)

        scope.launch {
            deployContract()
        }

    }

    private fun getCredentialsFromPrivateKey(): Credentials {
        return Credentials.create(PRIVATE_KEY)
    }

    private fun deployContract() {

        web3j =
            Web3j.build(HttpService(ALCHEMY_API))

        val deployedContractAddress = N.deploy(
            web3j,
            getCredentialsFromPrivateKey(),
            DefaultGasProvider(),
            "Vahid", "VHD"
        ).send()

        Log.d(
            "TAG",
            "Contract address is deployed at Address: ${deployedContractAddress.contractAddress} "
        )

        val transactionReceipt: TransactionReceipt = deployedContractAddress.mint(
            "contract address",
            tokenId.toBigInteger(),
            "your ipfs url"
        ).send()

        Log.d(
            "TAG",
            "mint transactionReceipt: ${transactionReceipt.toString()} "
        )

    }

}