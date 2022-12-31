package info.vahidmohammadi.nft

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider

class MainActivity : AppCompatActivity() {

    // Wallet Private Key
    private val PRIVATE_KEY =
        "3d80a2267450921e58f903fe5e56e81b1f05c24b2ff5d32795825529d7271fb1"

    // Alchemy API Https url
    private val ALCHEMY_API = "https://eth-goerli.g.alchemy.com/v2/Tp2nVMKlBlbBjq3a6JXhH9CRKjdi9MBA"
    private lateinit var web3j: Web3j

    //CoroutineScope to do network related
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val deployedContractAddress = NFT.deploy(
            web3j,
            getCredentialsFromPrivateKey(),
            DefaultGasProvider()
        )
            .send()
            .contractAddress

        Log.d("TAG", "Contract address is deployed at Address: $deployedContractAddress")

    }

}