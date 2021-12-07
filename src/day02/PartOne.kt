package day02

import readInput

sealed class SubmarineCommand(open val value: Int)
data class Forward(override val value: Int) : SubmarineCommand(value)
data class Down(override val value: Int) : SubmarineCommand(value)
data class Up(override val value: Int) : SubmarineCommand(value)

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

    val horizontalPosition = submarineCommands
        .filterIsInstance<Forward>()
        .sumOf { it.value }
    val depth = submarineCommands
        .filter { it is Down || it is Up }
        .sumOf {
            when (it) {
                is Down -> it.value
                else -> -it.value
            }
        }

    println(horizontalPosition * depth)
}