package day09

import readInput

enum class PointDifference { BIGGER, LOWER, SKIP }

fun main() {
    val testInput = readInput("day09", "PartNineTestData")

    val inputMatrix: Array<Array<Int>> = testInput.buildInputMatrix()
    print(inputMatrix.findLowPoints())
}

private fun List<String>.buildInputMatrix(): Array<Array<Int>> {
    val inputMatrix = Array(size) { Array(first().length) { 0 } }

    forEachIndexed { columnIndex, testString ->
        testString.forEachIndexed { roxIndex, c ->
            inputMatrix[columnIndex][roxIndex] = c.digitToInt()
        }
    }

    return inputMatrix
}

private fun Array<Array<Int>>.findLowPoints(): Int {
    val columnIndices = this.indices
    val rowIndices = this.first().indices
    val columnMin = columnIndices.minOf { it }
    val columnMax = columnIndices.maxOf { it }
    val rowMin = rowIndices.minOf { it }
    val rowMax = rowIndices.maxOf { it }

    var result = 0

    columnIndices.forEach { columnValue ->
        rowIndices.forEach { rowValue ->
            val isLeftPointBigger =
                if (rowValue > rowMin) isPointDifferenceSmallerOrBigger(
                    columnValue,
                    rowValue,
                    columnValue,
                    rowValue - 1
                )
                else PointDifference.SKIP
            val isRightPointBigger =
                if (rowValue < rowMax) isPointDifferenceSmallerOrBigger(
                    columnValue,
                    rowValue,
                    columnValue,
                    rowValue + 1
                )
                else PointDifference.SKIP
            val isTopPointBigger =
                if (columnValue > columnMin) isPointDifferenceSmallerOrBigger(
                    columnValue,
                    rowValue,
                    columnValue - 1,
                    rowValue
                )
                else PointDifference.SKIP
            val isBottomPointBigger =
                if (columnValue < columnMax) isPointDifferenceSmallerOrBigger(
                    columnValue,
                    rowValue,
                    columnValue + 1,
                    rowValue
                )
                else PointDifference.SKIP

            if (isLeftPointBigger.shouldTakeLowestPoint() &&
                isRightPointBigger.shouldTakeLowestPoint() &&
                isTopPointBigger.shouldTakeLowestPoint() &&
                isBottomPointBigger.shouldTakeLowestPoint()
            ) {
                result += this[columnValue][rowValue] + 1
            }
        }
    }

    return result
}

private fun Array<Array<Int>>.isPointDifferenceSmallerOrBigger(
    columnValue: Int,
    rowValue: Int,
    otherColumnValue: Int,
    otherRowValue: Int,
): PointDifference =
    if (this[columnValue][rowValue] < this[otherColumnValue][otherRowValue]) PointDifference.LOWER
    else PointDifference.BIGGER

private fun PointDifference.shouldTakeLowestPoint(): Boolean =
    this == PointDifference.LOWER || this == PointDifference.SKIP
