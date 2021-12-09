package day03

import readInput

fun main() {
    val testInput = readInput("day03", "PartThreeTestData")
    val indices = testInput.first().indices
    val gammaRate = buildString {
        for (indice in indices) {
            append(testInput.findMostCommonBit(indice))
        }
    }
    val epsilonRate = gammaRate.invertBinaryValue()

    println(gammaRate.toInt(2) * epsilonRate.toInt(2))
}

fun List<String>.findMostCommonBit(indice: Int): String {
    var numberOfZeroes = 0
    var numberOfOnes = 0

    forEach {
        when (it[indice]) {
            '0' -> numberOfZeroes++
            '1' -> numberOfOnes++
        }
    }

    return if (numberOfOnes > numberOfZeroes) "1" else "0"
}

fun String.invertBinaryValue(): String =
    buildString {
        this@invertBinaryValue.forEach {
            append(if (it == '0') "1" else "0")
        }
    }



