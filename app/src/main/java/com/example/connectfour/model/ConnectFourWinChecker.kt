package com.example.connectfour.model

class ConnectFourWinChecker : WinChecker {
    override fun checkWin(board: Board, player: Player): Boolean {
        val target = if (player == Player.RED) CellState.RED else CellState.YELLOW
        return checkHorizontal(board, target) ||
               checkVertical(board, target) ||
               checkDiagonalDownRight(board, target) ||
               checkDiagonalDownLeft(board, target)
    }

    private fun checkHorizontal(board: Board, target: CellState): Boolean {
        for (row in 0 until Board.ROWS) {
            var count = 0
            for (col in 0 until Board.COLUMNS) {
                if (board.getCell(row, col) == target) {
                    count++
                    if (count >= 4) return true
                } else {
                    count = 0
                }
            }
        }
        return false
    }

    private fun checkVertical(board: Board, target: CellState): Boolean {
        for (col in 0 until Board.COLUMNS) {
            var count = 0
            for (row in 0 until Board.ROWS) {
                if (board.getCell(row, col) == target) {
                    count++
                    if (count >= 4) return true
                } else {
                    count = 0
                }
            }
        }
        return false
    }

    private fun checkDiagonalDownRight(board: Board, target: CellState): Boolean {
        // ↘ Diagonal: top-left to bottom-right
        // Start from each cell and check 4 in a row going down-right
        for (row in 0 until Board.ROWS) {
            for (col in 0 until Board.COLUMNS) {
                if (canFormDiagonalDownRight(row, col)) {
                    var count = 0
                    var r = row
                    var c = col
                    while (r < Board.ROWS && c < Board.COLUMNS && board.getCell(r, c) == target) {
                        count++
                        if (count >= 4) return true
                        r++
                        c++
                    }
                }
            }
        }
        return false
    }

    private fun canFormDiagonalDownRight(startRow: Int, startCol: Int): Boolean {
        // Need at least 4 cells available in the diagonal direction
        val remainingRows = Board.ROWS - startRow
        val remainingCols = Board.COLUMNS - startCol
        return remainingRows >= 4 && remainingCols >= 4
    }

    private fun checkDiagonalDownLeft(board: Board, target: CellState): Boolean {
        // ↙ Diagonal: top-right to bottom-left
        for (row in 0 until Board.ROWS) {
            for (col in 0 until Board.COLUMNS) {
                if (canFormDiagonalDownLeft(row, col)) {
                    var count = 0
                    var r = row
                    var c = col
                    while (r < Board.ROWS && c >= 0 && board.getCell(r, c) == target) {
                        count++
                        if (count >= 4) return true
                        r++
                        c--
                    }
                }
            }
        }
        return false
    }

    private fun canFormDiagonalDownLeft(startRow: Int, startCol: Int): Boolean {
        // Need at least 4 rows going down and 4 columns going left
        val remainingRows = Board.ROWS - startRow
        return remainingRows >= 4 && startCol >= 3
    }
}