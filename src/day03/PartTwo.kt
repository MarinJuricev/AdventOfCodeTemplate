package day03

import readInput

enum class LifeSupportRating { OXYGEN, CO2 }

fun main() {
    val testInput = readInput("day03", "PartThreeTestData")
    val indices = testInput.first().indices
    val oxygenGeneratorRating = testInput.extractLifeSupportRating(
        indices.first,
        LifeSupportRating.OXYGEN
    )
    val co2ScrubberRating = testInput.extractLifeSupportRating(
        indices.first,
        LifeSupportRating.CO2
    )

    println(oxygenGeneratorRating.first().toInt(2) * co2ScrubberRating.first().toInt(2))
}

fun List<String>.extractLifeSupportRating(
    indice: Int,
    lifeSupportRatingToExtract: LifeSupportRating
): List<String> {
    while (size != 1) {
        var numberOfZeroes = 0
        var numberOfOnes = 0

        forEach {
            when (it[indice]) {
                '0' -> numberOfZeroes++
                '1' -> numberOfOnes++
            }
        }
        val filteredColumn = filterDependingOnLifeSupportRating(
            numberOfZeroes,
            numberOfOnes,
            indice,
            lifeSupportRatingToExtract,
        )
        val updatedIndice = indice + 1
        return filteredColumn.extractLifeSupportRating(updatedIndice, lifeSupportRatingToExtract)
    }

    return this
}

private fun List<String>.filterDependingOnLifeSupportRating(
    numberOfZeroes: Int,
    numberOfOnes: Int,
    indice: Int,
    lifeSupportRatingToExtract: LifeSupportRating
): List<String> =
    when {
        numberOfZeroes > numberOfOnes -> filter {
            if (lifeSupportRatingToExtract == LifeSupportRating.OXYGEN)
                it[indice] == '0'
            else
                it[indice] == '1'
        }
        else -> filter {
            if (lifeSupportRatingToExtract == LifeSupportRating.OXYGEN)
                it[indice] == '1'
            else
                it[indice] == '0'

        }
    }
