package day08

import readInput

val zero = "123567"
val one = "36"
val two = "13457"
val three = "13467"
val four = "2346"
val five = "12467"
val six = "124567"
val seven = "136"
val eight = "1234567"
val nine = "123467"

/**
 * The input signalPosition and rest of the signals are modeled like this:
 *
 *   1111
 *  2    3
 *  2    3
 *   4444
 *  5    6
 *  5    6
 *   7777
 * */

fun main() {
    var result = 0
    var intermediateResult = ""
    val testInput = readInput("day08", "PartEightTestData")
    val mappedSignal = mutableMapOf(
        0 to "",
        1 to "",
        2 to "",
        3 to "",
        4 to "",
        5 to "",
        6 to "",
        7 to "",
        8 to "",
    )
    val signalPosition = mutableMapOf(
        1 to "",
        2 to "",
        3 to "",
        4 to "",
        5 to "",
        6 to "",
        7 to "",
    )
    testInput.forEachIndexed { index, _ ->
        val formattedOutput = testInput.map { it.split(" | ") }
        val allSignals = formattedOutput.map { it.first().split(" ") }
        val outPutToTest = formattedOutput.map { it[1].split(" ") }

        allSignals[index].forEach { signals: String ->
            when (signals.length) {
                2 -> mappedSignal[2] = signals
                4 -> mappedSignal[4] = signals
                3 -> mappedSignal[7] = signals
                7 -> mappedSignal[8] = signals
            }
        }

        signalPosition[1] = mappedSignal[7]!!.removeCommonCharacters(mappedSignal[2]!!)
        signalPosition[7] = extract6thMark(allSignals[index], mappedSignal)
        signalPosition[4] = extract3rdMark(allSignals[index], mappedSignal)
        signalPosition[2] = extract1stMark(allSignals[index], mappedSignal, signalPosition)
        signalPosition[6] = extract5hMark(allSignals[index], signalPosition)
        signalPosition[3] = extract2ndMark(mappedSignal, signalPosition)
        signalPosition[5] = extract4thMark(mappedSignal, signalPosition)

        outPutToTest[index].forEach { output ->
            var localResult = ""
            output.forEach { outputChar ->
                signalPosition.forEach {
                    if (outputChar.toString() == it.value) {
                        localResult += it.key
                    }
                }
            }
            val sortedLocalResult = localResult.toCharArray().sorted().joinToString("")
            intermediateResult += when {
                sortedLocalResult == zero -> "0"
                sortedLocalResult == one -> "1"
                sortedLocalResult == two -> "2"
                sortedLocalResult == three -> "3"
                sortedLocalResult == four -> "4"
                sortedLocalResult == five -> "5"
                sortedLocalResult == six -> "6"
                sortedLocalResult == seven -> "7"
                sortedLocalResult == nine -> "9"
                else -> "8"
            }
        }
        result += intermediateResult.toIntOrNull() ?: 0
        intermediateResult = ""
    }

    print(result)
}

fun String.stringToCharacterSet(): Set<Char> {
    val set: MutableSet<Char> = HashSet()
    for (c in toCharArray()) {
        set.add(c)
    }
    return set
}

fun containsAllChars(container: String, containee: String): Boolean {
    return container.stringToCharacterSet().containsAll(containee.stringToCharacterSet())
}

fun String.removeCommonCharacters(containee: String): String {
    var result = ""

    forEach { outter ->
        if (!containee.contains(outter)) {
            result += outter
        }
    }

    return result
}

fun String.removeDifferentCharacters(containee: String): String {
    var result = ""

    forEach { outter ->
        if (containee.contains(outter)) {
            result += outter
        }
    }

    return result
}

private fun extract6thMark(
    signals: List<String>,
    mappedSignal: MutableMap<Int, String>,
): String {
    val threeSignal = signals
        .filter { it.length == 5 }
        .filter { containsAllChars(it, mappedSignal[7]!!) }
        .map { it.removeCommonCharacters(mappedSignal[7]!!) }
        .first()

    return threeSignal.removeCommonCharacters(mappedSignal[4]!!)
}


fun extract3rdMark(
    signals: List<String>,
    mappedSignal: MutableMap<Int, String>,
): String {
    val threeSignal = signals
        .filter { it.length == 5 }
        .first { containsAllChars(it, mappedSignal[7]!!) }

    return threeSignal
        .removeDifferentCharacters(mappedSignal[4]!!)
        .removeCommonCharacters(mappedSignal[2]!!)
}

fun extract1stMark(
    signals: List<String>,
    mappedSignal: MutableMap<Int, String>,
    signalPosition: MutableMap<Int, String>,
): String {
    val threeSignal = signals
        .filter { it.length == 5 }
        .first { containsAllChars(it, mappedSignal[7]!!) }

    val difference = threeSignal
        .removeCommonCharacters(mappedSignal[4]!!) + mappedSignal[4]!!.removeCommonCharacters(threeSignal)

    return difference
        .removeCommonCharacters("${signalPosition[1]!!}${signalPosition[7]!!}")
}

fun extract5hMark(
    signals: List<String>,
    signalPosition: MutableMap<Int, String>
): String {
    val maskForFive = signalPosition[1]!!
        .plus(signalPosition[2]!!)
        .plus(signalPosition[4]!!)
        .plus(signalPosition[7]!!)

    val fiveSignal = signals
        .filter { it.length == 5 }
        .first { containsAllChars(it, maskForFive) }

    return fiveSignal.removeCommonCharacters(maskForFive)
}

fun extract2ndMark(
    mappedSignal: MutableMap<Int, String>,
    signalPosition: MutableMap<Int, String>,
): String = mappedSignal[2]!!.removeCommonCharacters(signalPosition[6]!!)

fun extract4thMark(
    mappedSignal: MutableMap<Int, String>,
    signalPosition: MutableMap<Int, String>
): String {
    val signal8Mask = signalPosition[1]!!
        .plus(signalPosition[2]!!)
        .plus(signalPosition[3]!!)
        .plus(signalPosition[4]!!)
        .plus(signalPosition[6]!!)
        .plus(signalPosition[7]!!)

    return mappedSignal[8]!!.removeCommonCharacters(signal8Mask)
}