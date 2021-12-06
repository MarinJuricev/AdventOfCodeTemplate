package day01

import readInputAsInts

fun main() {
    val testInput = readInputAsInts("day01", "PartOneTestData")

    println(testInput
        .windowed(2)
        .count { (a, b) -> a < b }
    )
}