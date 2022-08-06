import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.nio.ByteBuffer
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement

class ECDH(password: String? = null) {
    private var keyPair: KeyPair

    init {
        Security.insertProviderAt(BouncyCastleProvider(), 14)
        val spec = ECGenParameterSpec("secp256k1")
        val generator = KeyPairGenerator.getInstance("EC", "BC")
        if (password != null) {
            generator.initialize(spec, SecureRandom(password.toByteArray()))
        }
        generator.initialize(spec)
        keyPair = generator.generateKeyPair()

    }


    val publicKey: ByteArray
        get() = keyPair.public.encoded


    val privateKey: ByteArray
        get() = keyPair.private.encoded

    companion object {
        @JvmStatic
        fun generateSecretKey(outPrivateKey: String, theirPublicKey: String): ByteArray {
            Security.addProvider(BouncyCastleProvider())

            val factory = KeyFactory.getInstance("ECDH", "BC")
            val keyAgreement = KeyAgreement.getInstance("ECDH", "BC")
            keyAgreement.init(factory.generatePrivate(PKCS8EncodedKeySpec(outPrivateKey.fromBase64())))
            keyAgreement.doPhase(factory.generatePublic(X509EncodedKeySpec(theirPublicKey.fromBase64())), true)
            return keyAgreement.generateSecret()
        }

        @JvmStatic
        fun generateFinalKey(outPublicKey: String, theirPublicKey: String, shareSecretKey: String): ByteArray {
            val sha256 = MessageDigest.getInstance("SHA-256")
            val outPublicKeyBytes = outPublicKey.fromBase64()
            val theirPublicKeyBytes = theirPublicKey.fromBase64()
            val shareSecretKeyBytes = shareSecretKey.fromBase64()
            val listSortKey = ByteBuffer.allocate(outPublicKeyBytes.size + theirPublicKeyBytes.size + shareSecretKeyBytes.size).put(outPublicKeyBytes).put(theirPublicKeyBytes).put(shareSecretKeyBytes).array().toList().sorted().toByteArray()
            sha256.update(listSortKey)
            return sha256.digest()
        }
    }
}