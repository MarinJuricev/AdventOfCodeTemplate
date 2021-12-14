package day06

import readInput

private const val DAYS_TO_CALCULATE = 256

fun main() {
    val testInput = readInput("day06", "PartSixTestData")
    val lanternFishes: List<Int> = testInput
        .single()
        .extractNumbers()
        .map { it.toInt() }

    val lanternFishesNumber = LongArray(9)
    lanternFishes.forEach { lanternFishesNumber[it]++ }

    (1..DAYS_TO_CALCULATE).forEach { _ ->
        val zeros = lanternFishesNumber[0]
        (1..8).forEach { lanternFishesNumber[it - 1] = lanternFishesNumber[it] }
        lanternFishesNumber[6] += zeros
        lanternFishesNumber[8] = zeros
    }

    print(lanternFishesNumber.sum())
}
