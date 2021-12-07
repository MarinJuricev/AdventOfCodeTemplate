package day02

import readInput

fun main() {
    val testInput = readInput("day02", "PartTwoTestData")
    val submarineCommands = testInput
        .map { it.split(" ") }
        .map {
            when (it.first()) {
                "forward" -> Forward(it[1].toIntOrNull() ?: 0)
                "down" -> Down(it[1].toIntOrNull() ?: 0)
                "up" -> Up(it[1].toIntOrNull() ?: 0)
                else -> error("Unexpected command found :${it.first()}")
            }
        }

    var aim = 0
    var depth = 0
    var horizontalPosition = 0

    submarineCommands.forEach {
        when (it) {
            is Down -> aim += it.value
            is Up -> aim -= it.value
            is Forward -> {
                horizontalPosition += it.value
                depth += aim * it.value
            }
        }
    }

    println(horizontalPosition * depth)
}