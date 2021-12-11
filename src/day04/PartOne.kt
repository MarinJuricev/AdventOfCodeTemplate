package day04

import readInput
import java.util.regex.Pattern

data class Boards(val value: List<Board>)
data class Board(
    val columnIndex: Int,
    val rowIndex: Int,
    val value: String,
    val isMarked: Boolean = false,
)

fun main() {
    val testInput = readInput("day04", "PartFourTestData")

    val drawnNumbers = testInput
        .first()
        .extractNumbers()

    val boards = testInput
        .drop(2)
        .buildBingoBoards()

    val finalScore = findFirstWinningBoard(drawnNumbers, boards)

    print(finalScore)
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

fun List<String>.buildBingoBoards(): List<Boards> {
    var result = mutableListOf<Boards>()
    var currentIndex = 0

    while (currentIndex <= lastIndex) {
        val boardList = subList(currentIndex, currentIndex + 5)
            .map { it.extractNumbers() }
            .flatMapIndexed { rowIndex, strings ->
                strings.mapIndexed { columnIndex, boardValue ->
                    Board(columnIndex, rowIndex, boardValue)
                }
            }
        result.add(Boards(boardList))
        currentIndex += 6
    }

    return result
}

fun findFirstWinningBoard(
    drawnNumbers: List<String>,
    boards: List<Boards>,
): Int {
    var boardToIterate = boards

    drawnNumbers.forEach { drawnNumber ->
        val updatedBoards = updateBoardWithDrawnNumber(boardToIterate, drawnNumber)
        repeat((0..4).count()) { index ->
            updatedBoards.forEach { board ->
                val column = board.filter { it.columnIndex == index }
                val row = board.filter { it.rowIndex == index }
                val possibleColumnWin = column.all { it.isMarked }
                val possibleRowWin = row.all { it.isMarked }

                if (possibleColumnWin || possibleRowWin) {
                    return board.filter { !it.isMarked }.sumOf { it.value.toInt() * drawnNumber.toInt() }
                }
            }
        }

        boardToIterate = updatedBoards.map { Boards(it) }
    }

    error("No Final score found")
}

fun updateBoardWithDrawnNumber(
    boardList: List<Boards>,
    drawnNumber: String
) = boardList.map { boards ->
    boards.value.map { board ->
        if (board.value == drawnNumber) {
            board.copy(isMarked = true)
        } else {
            board
        }
    }
}