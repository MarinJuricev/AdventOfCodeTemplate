package day05

import readInput
import java.util.regex.Pattern

data class Board(
    val boardItem: List<BoardItem>
)

data class BoardItem(
    val columnIndex: Int,
    val rowIndex: Int,
    var numberOfTimesMarked: Int = 0,
)

data class Line(
    val startPoint: Point,
    val endPoint: Point
)

data class Point(val x: Int, val y: Int)
enum class StartPoint { X, Y }

fun main() {
    val testInput = readInput("day05", "PartFiveTestData")

    val sanitizedTestInput = testInput
        .map { it.extractNumbers() }.filter { it.isNotEmpty() }

    val lines = sanitizedTestInput.extractCorrectLines()
    val maximumNumber = lines.maxOf { it.returnMaximumValue() }

    val board = buildBoard(maximumNumber)

    val result = calculateResult(board, lines)

    print(result)
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

fun List<List<String>>.extractCorrectLines(): List<Line> {
    return mapNotNull {
        if (it[0] == it[2] || it[1] == it[3]) {
            val startPoint = Point(it[0].toInt(), it[1].toInt())
            val endPoint = Point(it[2].toInt(), it[3].toInt())

            Line(startPoint, endPoint)
        } else
            null
    }
}

fun Point.returnMaximumValue(): Int = if (x >= y) x else y
fun Line.returnMaximumValue(): Int {
    val startPointMaximumValue = startPoint.returnMaximumValue()
    val endPointMaximumValue = endPoint.returnMaximumValue()

    return if (startPointMaximumValue >= endPointMaximumValue) {
        startPointMaximumValue
    } else {
        endPointMaximumValue
    }
}

fun buildBoard(maximumNumber: Int): Board {
    val boardItems = mutableListOf<BoardItem>()

    (0..maximumNumber).forEach { columnIndex ->
        (0..maximumNumber).forEach { rowIndex ->
            boardItems.add(BoardItem(columnIndex, rowIndex))
        }
    }

    return Board(boardItems)
}

fun calculateResult(board: Board, lines: List<Line>): Int {
    val boardItems = board.boardItem

    lines.forEach { line ->
        val isHorizontalLine = line.startPoint.y == line.endPoint.y
        if (isHorizontalLine) {
            val startIndexAndEndIndex = calculateStartAndEndIndex(line, StartPoint.X)
            (startIndexAndEndIndex.first..startIndexAndEndIndex.second).forEach { rowIndex ->
                val boardItem = boardItems.find { it.columnIndex == line.startPoint.y && rowIndex == it.rowIndex }
                boardItem?.numberOfTimesMarked = (boardItem?.numberOfTimesMarked ?: 0) + 1
            }
        } else {
            val startIndexAndEndIndex = calculateStartAndEndIndex(line, StartPoint.Y)

            (startIndexAndEndIndex.first..startIndexAndEndIndex.second).forEach { columnIndex ->
                val boardItem = boardItems.find { it.rowIndex == line.startPoint.x && columnIndex == it.columnIndex }
                boardItem?.numberOfTimesMarked = (boardItem?.numberOfTimesMarked ?: 0) + 1
            }
        }
    }

    return boardItems.count { it.numberOfTimesMarked > 1 }
}

fun calculateStartAndEndIndex(line: Line, startPoint: StartPoint): Pair<Int, Int> =
    when (startPoint) {
        StartPoint.X -> {
            if (line.startPoint.x > line.endPoint.x)
                line.endPoint.x to line.startPoint.x
            else
                line.startPoint.x to line.endPoint.x
        }
        StartPoint.Y -> {
            if (line.startPoint.y > line.endPoint.y)
                line.endPoint.y to line.startPoint.y
            else
                line.startPoint.y to line.endPoint.y
        }
    }
