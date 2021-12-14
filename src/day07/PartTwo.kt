package day07

import readInput

fun main() {
    val testInput = readInput("day07", "PartSevenTestData")
    val crabs = testInput
        .single()
        .extractNumbers()
        .map { it.toInt() }

    val maxNumber = crabs.maxOrNull() ?: return

    var result = Int.MAX_VALUE
    var innerResult = Int.MAX_VALUE
    var crabMoveRate = 1

    for (outerIndex in 0..maxNumber) {
        if (innerResult < result) {
            result = innerResult
        }
        innerResult = 0
        for (innerIndex in crabs.indices) {
            repeat(kotlin.math.abs(crabs[innerIndex] - outerIndex)) {
                innerResult +=  crabMoveRate
                crabMoveRate++
            }
            crabMoveRate = 1
        }
    }

    print(result)
}