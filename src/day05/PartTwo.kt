package day05

import readInput

fun main() {
    val testInput = readInput("day05", "PartFiveTestData")

    val sanitizedTestInput = testInput
        .map { it.extractNumbers() }.filter { it.isNotEmpty() }

    val lines = sanitizedTestInput.extractCorrectLinesWithDiagonal()
    val maximumNumber = lines.maxOf { it.returnMaximumValue() }

    val board = buildBoard(maximumNumber)

    val result = calculateResultWithDiagonal(board, lines)

    print(result)
}

fun List<List<String>>.extractCorrectLinesWithDiagonal(): List<Line> {
    return map {
        val startPoint = Point(it[0].toInt(), it[1].toInt())
        val endPoint = Point(it[2].toInt(), it[3].toInt())

        Line(startPoint, endPoint)
    }
}

fun calculateResultWithDiagonal(board: Board, lines: List<Line>): Int {
    val boardItems = board.boardItem

    lines.forEach { line ->
        if (isHorizontalLine(line)) {
            val startIndexAndEndIndex = calculateStartAndEndIndex(line, StartPoint.X)
            (startIndexAndEndIndex.first..startIndexAndEndIndex.second).forEach { rowIndex ->
                val boardItem = boardItems.find { it.columnIndex == line.startPoint.y && rowIndex == it.rowIndex }
                boardItem?.numberOfTimesMarked = (boardItem?.numberOfTimesMarked ?: 0) + 1
            }
        } else if (isVerticalLine(line)) {
            val startIndexAndEndIndex = calculateStartAndEndIndex(line, StartPoint.Y)
            (startIndexAndEndIndex.first..startIndexAndEndIndex.second).forEach { columnIndex ->
                val boardItem = boardItems.find { it.rowIndex == line.startPoint.x && columnIndex == it.columnIndex }
                boardItem?.numberOfTimesMarked = (boardItem?.numberOfTimesMarked ?: 0) + 1
            }
        } else {
            var startPointRow = line.startPoint.x
            var startPointColumn = line.startPoint.y

            val delta = calculateDelta(line)
            for (i in delta downTo 0) {
                val boardItem = boardItems.find { it.columnIndex == startPointColumn && it.rowIndex == startPointRow }
                boardItem?.numberOfTimesMarked = (boardItem?.numberOfTimesMarked ?: 0) + 1

                if (startPointRow >= line.endPoint.x) {
                    startPointRow--
                } else if (startPointRow <= line.endPoint.x) {
                    startPointRow++
                }

                if (startPointColumn >= line.endPoint.y) {
                    startPointColumn--
                } else if (startPointColumn <= line.endPoint.y) {
                    startPointColumn++
                }
            }
        }
    }

    return boardItems.count { it.numberOfTimesMarked > 1 }
}

private fun isVerticalLine(line: Line) = line.startPoint.x == line.endPoint.x

private fun isHorizontalLine(line: Line) = line.startPoint.y == line.endPoint.y

private fun calculateDelta(line: Line) =
    if (line.startPoint.x > line.endPoint.x) line.startPoint.x - line.endPoint.x else line.endPoint.x - line.startPoint.x