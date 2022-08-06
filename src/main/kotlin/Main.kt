import java.io.File

fun main() {
val file = File("output.txt")
    println("Path: ${file.absolutePath}")




    for (i in 1..10) {
        file.appendText("Test case id: $i\n")
        println("Test case id: $i")
        val alice = ECDH()
        val bob = ECDH()
        val alicePublicKey = alice.publicKey.toBase64()
        val bobPublicKey = bob.publicKey.toBase64()
        println("Alice public key: ${alicePublicKey.fromBase64().toHex()}")
        println("Bob public key: ${bobPublicKey.fromBase64().toHex()}")
        file.appendText("Alice's public key: ${alicePublicKey.fromBase64().toHex()}\n")
        file.appendText("Bob's public key: ${bobPublicKey.fromBase64().toHex()}\n")


        val alicePrivateKey = alice.privateKey.toBase64()
        val bobPrivateKey = bob.privateKey.toBase64()

        println("Alice private key: ${alicePrivateKey.fromBase64().toHex()}")
        println("Bob private key: ${bobPrivateKey.fromBase64().toHex()}")
        file.appendText("Alice's private key: ${alicePrivateKey.fromBase64().toHex()}\n")
        file.appendText("Bob's private key: ${bobPrivateKey.fromBase64().toHex()}\n")

        val aliceSharedKey = ECDH.generateSecretKey(alicePrivateKey, bobPublicKey).toBase64()
        val bobSharedKey = ECDH.generateSecretKey(bobPrivateKey, alicePublicKey).toBase64()
        println("Alice shared key: ${aliceSharedKey.fromBase64().toHex()}")
        println("Bob shared key: ${bobSharedKey.fromBase64().toHex()}")
        file.appendText("Alice's shared key: ${aliceSharedKey.fromBase64().toHex()}\n")
        file.appendText("Bob's shared key: ${bobSharedKey.fromBase64().toHex()}\n")

        val aliceFinalKey = ECDH.generateFinalKey(alicePublicKey, bobPublicKey, aliceSharedKey).toBase64()
        val bobFinalKey = ECDH.generateFinalKey(bobPublicKey, alicePublicKey, bobSharedKey).toBase64()
        println("Alice final key: ${aliceFinalKey.fromBase64().toHex()}")
        println("Bob final key: ${bobFinalKey.fromBase64().toHex()}\n")
        file.appendText("Alice's final key: ${aliceFinalKey.fromBase64().toHex()}\n")
        file.appendText("Bob's final key: ${bobFinalKey.fromBase64().toHex()}\n\n")
    }
}



