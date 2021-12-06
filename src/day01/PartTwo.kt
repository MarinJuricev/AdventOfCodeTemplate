package day01

import readInput

fun main() {
    val testInput = readInput("day01", "PartOneTestData")

    println(
        testInput
            .windowed(3)
            .map { (a, b, c) -> a.toInt() + b.toInt() + c.toInt() }
            .windowed(2)
            .count { (a, b) -> a < b }
    )
}