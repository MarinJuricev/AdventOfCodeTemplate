package day08

import readInput

fun main() {
    val testInput = readInput("day08", "PartEightTestData")

    val formattedOutput = testInput.map { it.split(" | ") }
    val outPutToTest = formattedOutput.map { it[1].split(" ") }

    val result = IntArray(9)

    outPutToTest.forEach { outputs ->
        outputs.forEach { output ->
            when(output.length) {
                2 -> result[2] += 1
                4 -> result[4] += 1
                3 -> result[7] += 1
                7 -> result[8] += 1
            }
        }
    }

    print(result.sum())
}