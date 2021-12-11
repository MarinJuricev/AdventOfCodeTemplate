package day04

import readInput

fun main() {
    val testInput = readInput("day04", "PartFourTestData")

    val drawnNumbers = testInput
        .first()
        .extractNumbers()

    val boards = testInput
        .drop(2)
        .buildBingoBoards()

    val finalScore = findLastWinningBoard(drawnNumbers, boards)

    print(finalScore)
}

fun findLastWinningBoard(
    drawnNumbers: List<String>,
    boards: List<Boards>,
): Int {
    var boardToIterate = boards
    val winningBoards = mutableListOf<Int>()

    drawnNumbers.forEach { drawnNumber ->
        val updatedBoards = updateBoardWithDrawnNumber(boardToIterate, drawnNumber)
        repeat((0..4).count()) { index ->
            updatedBoards.forEachIndexed { boardIndex, board ->
                val column = board.filter { it.columnIndex == index }
                val row = board.filter { it.rowIndex == index }
                val possibleColumnWin = column.all { it.isMarked }
                val possibleRowWin = row.all { it.isMarked }

                if (possibleColumnWin || possibleRowWin) {
                    if (!winningBoards.contains(boardIndex)) {
                        if (isLastWinningBoard(winningBoards, boardToIterate)) {
                            return board.filter { !it.isMarked }.sumOf { it.value.toInt() * drawnNumber.toInt() }
                        }
                        winningBoards.add(boardIndex)
                    }
                }
            }
        }

        boardToIterate = updatedBoards.map { Boards(it) }
    }

    error("No Final score found")
}

private fun isLastWinningBoard(
    winningBoards: MutableList<Int>,
    boardToIterate: List<Boards>
) = winningBoards.size == boardToIterate.size - 1