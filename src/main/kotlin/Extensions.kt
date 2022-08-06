import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.Comparator
import kotlin.random.Random

fun <T> List<T & Any>.argsort(): IntArray {
    val result = mutableListOf<Int>()
    for (i in 0 until this.size) {
        result.add(i)
    }
    result.sortWith(Comparator { o1, o2 ->
        val number1 = this[o1] as Number
        val number2 = this[o2] as Number
        when (number1) {
            is BigDecimal -> number1.compareTo(number2 as BigDecimal)
            is BigInteger -> number1.compareTo(number2 as BigInteger)
            is Double -> number1.compareTo(number2 as Double)
            is Float -> number1.compareTo(number2 as Float)
            is Long -> number1.compareTo(number2 as Long)
            is Int -> number1.compareTo(number2 as Int)
            is Short -> number1.compareTo(number2 as Short)
            is Byte -> number1.compareTo(number2 as Byte)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    })
    return result.toIntArray()
}


fun Byte.toIndex(): Int {
    return this.toUByte().toInt()
}

fun Any.toNumber(): Number? {
    return when (this) {
        is Int -> this
        is Long -> this
        is Float -> this
        is Double -> this
        is Short -> this
        is Byte -> this
        is BigInteger -> this
        is BigDecimal -> this
        is String -> this.toDoubleOrNull()
        else -> null
    }
}

fun randomBytes(count: Int): ByteArray {
    val bytes = ByteArray(count)
    Random.nextBytes(bytes)
    return bytes
}

fun checkSbox(sbox: List<UByte>): Boolean {
    val check = Array(256) { 0 }
    for (i in 0 until 256) {
        check[sbox[i].toInt()]++
    }
    for (i in 0 until 256) {
        if (check[i] != 1) {
            return false
        }
    }
    return true
}

// To hex like ab cd 01
fun ByteArray.toHex(): String {
    val sb = StringBuilder()
    for (i in 0 until this.size) {
        sb.append(String.format("%02x", this[i]))
        sb.append(" ")
    }
    return sb.toString()
}

fun ByteArray.toBase64(): String = Base64.getEncoder().encodeToString(this)

fun String.fromBase64(): ByteArray = Base64.getDecoder().decode(this)