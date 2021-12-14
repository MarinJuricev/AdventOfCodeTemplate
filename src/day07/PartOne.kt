package day07

import readInput
import java.util.regex.Pattern

fun main() {
    val testInput = readInput("day07", "PartSevenTestData")

    val crabs = testInput
        .single()
        .extractNumbers()
        .map { it.toInt() }

    var result = Int.MAX_VALUE
    var innerResult = Int.MAX_VALUE

    for (outerIndex in crabs.indices) {
        if (innerResult < result) {
            result = innerResult
        }
        innerResult = 0
        for (innerIndex in crabs.indices) {
            if (innerIndex == outerIndex || crabs[outerIndex] == crabs[innerIndex])
                continue
            innerResult = kotlin.math.abs(crabs[outerIndex] - crabs[innerIndex])
        }
    }

    print(result)
}

fun String.extractNumbers(): List<String> {
    val result = mutableListOf<String>()
    val pattern = Pattern.compile("\\d+")
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        result.add(matcher.group())
    }

    return result
}