package day09

import readInput

enum class BasinSearchDirection { LEFT, RIGHT, UP, DOWN }
data class Basin(val value: Int, var isPartOfABasin: Boolean = false)

fun main() {
    val testInput = readInput("day09", "PartNineTestData")

    val inputMatrix: Array<Array<Basin>> = testInput.buildBasinMatrix()
    print(inputMatrix.findHighestBasins())
}

private fun List<String>.buildBasinMatrix(): Array<Array<Basin>> {
    val inputMatrix = Array(size) { Array(first().length) { Basin(0) } }

    forEachIndexed { columnIndex, testString ->
        testString.forEachIndexed { roxIndex, c ->
            inputMatrix[columnIndex][roxIndex] = Basin(c.digitToInt())
        }
    }

    return inputMatrix
}

private fun Array<Array<Basin>>.findHighestBasins(): Int {
    val columnIndices = this.indices
    val rowIndices = this.first().indices
    val columnMin = columnIndices.minOf { it }
    val columnMax = columnIndices.maxOf { it }
    val rowMin = rowIndices.minOf { it }
    val rowMax = rowIndices.maxOf { it }

    val basinSizes = mutableListOf<Int>()

    for (columnValue in columnIndices) {
        for (rowValue in rowIndices) {
            if (shouldSkipCurrentBasin(columnValue, rowValue))
                continue

            val leftBasinSize = extractBasinCount(
                columnValue,
                rowValue,
                columnMin,
                columnMax,
                rowMin,
                rowMax,
                BasinSearchDirection.LEFT
            )

            val rightBasinSize = extractBasinCount(
                columnValue,
                rowValue,
                columnMin,
                columnMax,
                rowMin,
                rowMax,
                BasinSearchDirection.RIGHT
            )

            val topBasinSize = extractBasinCount(
                columnValue,
                rowValue,
                columnMin,
                columnMax,
                rowMin,
                rowMax,
                BasinSearchDirection.UP
            )

            val bottomBasinSize = extractBasinCount(
                columnValue,
                rowValue,
                columnMin,
                columnMax,
                rowMin,
                rowMax,
                BasinSearchDirection.DOWN
            )

            basinSizes.add(leftBasinSize + rightBasinSize + topBasinSize + bottomBasinSize)
        }
    }

    return basinSizes
        .sorted()
        .takeLast(3)
        .sum()
}

private fun Array<Array<Basin>>.shouldSkipCurrentBasin(
    columnValue: Int,
    rowValue: Int
) = this[columnValue][rowValue].value == 9 || this[columnValue][rowValue].isPartOfABasin

private fun Array<Array<Basin>>.extractBasinCount(
    columnValue: Int,
    rowValue: Int,
    columnMin: Int,
    columnMax: Int,
    rowMin: Int,
    rowMax: Int,
    basinSearchDirection: BasinSearchDirection,
    basinCount: Int = 0,
): Int {
    if (shouldSkipCurrentBasin(columnValue, rowValue)) return basinCount

    return when (basinSearchDirection) {
        BasinSearchDirection.LEFT -> {
            if (rowValue - 1 > rowMin &&
                isValidBasinNeighbour(this[columnValue][rowValue].value, this[columnValue][rowValue - 1].value)
            ) {
                this[columnValue][rowValue].isPartOfABasin = true

                extractBasinCount(
                    columnValue,
                    rowValue - 1,
                    columnMin,
                    columnMax,
                    rowMin,
                    rowMax,
                    basinSearchDirection,
                    basinCount + 1
                )
            } else {
                basinCount
            }
        }
        BasinSearchDirection.RIGHT -> {
            if (rowValue + 1 < rowMax &&
                isValidBasinNeighbour(this[columnValue][rowValue].value, this[columnValue][rowValue + 1].value)
            ) {
                this[columnValue][rowValue].isPartOfABasin = true

                extractBasinCount(
                    columnValue,
                    rowValue + 1,
                    columnMin,
                    columnMax,
                    rowMin,
                    rowMax,
                    basinSearchDirection,
                    basinCount + 1
                )
            } else {
                basinCount
            }
        }
        BasinSearchDirection.UP -> {
            if (columnValue - 1 > columnMin &&
                isValidBasinNeighbour(this[columnValue][rowValue].value, this[columnValue - 1][rowValue].value)
            ) {
                this[columnValue][rowValue].isPartOfABasin = true

                extractBasinCount(
                    columnValue - 1,
                    rowValue,
                    columnMin,
                    columnMax,
                    rowMin,
                    rowMax,
                    basinSearchDirection,
                    basinCount + 1
                )
            } else {
                basinCount
            }
        }
        BasinSearchDirection.DOWN -> {
            if (columnValue + 1 < columnMax &&
                isValidBasinNeighbour(this[columnValue][rowValue].value, this[columnValue + 1][rowValue].value)
            ) {
                this[columnValue][rowValue].isPartOfABasin = true

                extractBasinCount(
                    columnValue + 1,
                    rowValue,
                    columnMin,
                    columnMax,
                    rowMin,
                    rowMax,
                    basinSearchDirection,
                    basinCount + 1
                )
            } else {
                basinCount
            }
        }
    }
}

private fun isValidBasinNeighbour(
    currentBasinValue: Int,
    neighbourBasinValue: Int
) = currentBasinValue + 1 == neighbourBasinValue || currentBasinValue - 1 == neighbourBasinValue