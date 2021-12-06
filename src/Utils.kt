import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput(
    path: String,
    name: String,
) = File("src/$path", "$name.txt").readLines()

fun readInputAsInts(
    path: String,
    name: String,
) = readInput(path, name)
    .map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
