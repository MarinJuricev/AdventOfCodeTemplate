package day06

import readInput
import java.util.regex.Pattern

private const val INITIAL_DAYS_UNTIL_NEW_SPAWN = 8
private const val DAYS_TO_CALCULATE = 80

@JvmInline
value class LanternFish(val daysUntilNewSpawn: Int)

fun main() {
    val testInput = readInput("day06", "PartSixTestData")
    val lanternFishes = testInput
        .single()
        .extractNumbers()
        .map { LanternFish(it.toInt()) }

    var listToReturn = lanternFishes.toMutableList()
    var numberOfFishesToAdd = 0

    (1..DAYS_TO_CALCULATE).forEach { day ->
        listToReturn = listToReturn.map { lanternFish ->
            val newDaysUntilNewSpawn = when (val result = lanternFish.daysUntilNewSpawn) {
                0 -> 6
                else -> result - 1
            }
            LanternFish(newDaysUntilNewSpawn)
        } as MutableList<LanternFish>
        if (numberOfFishesToAdd != 0) {
            repeat((numberOfFishesToAdd downTo 1).count()) {
                listToReturn.add(LanternFish(INITIAL_DAYS_UNTIL_NEW_SPAWN))
            }
        }
        numberOfFishesToAdd = listToReturn.count { it.daysUntilNewSpawn == 0 }
    }

    print(listToReturn.size)
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