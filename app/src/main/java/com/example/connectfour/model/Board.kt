package com.example.connectfour.model

data class Board(val cells: List<List<CellState>>) {
    companion object {
        const val COLUMNS = 7
        const val ROWS = 6

        fun empty(): Board {
            val emptyRow = List(COLUMNS) { CellState.EMPTY }
            val emptyCells = List(ROWS) { emptyRow }
            return Board(emptyCells)
        }
    }

    fun dropPiece(column: Int, player: Player): Board {
        // Check bounds
        if (column < 0 || column >= COLUMNS) {
            return this
        }

        // Find the lowest empty row (highest row index)
        var targetRow = -1
        for (row in ROWS - 1 downTo 0) {
            if (cells[row][column] == CellState.EMPTY) {
                targetRow = row
                break
            }
        }

        // Column is full
        if (targetRow == -1) {
            return this
        }

        // Create new board with the piece placed
        val newCells = cells.mapIndexed { rowIndex, row ->
            if (rowIndex == targetRow) {
                row.mapIndexed { colIndex, cell ->
                    if (colIndex == column) {
                        when (player) {
                            Player.RED -> CellState.RED
                            Player.YELLOW -> CellState.YELLOW
                        }
                    } else {
                        cell
                    }
                }
            } else {
                row
            }
        }

        return Board(newCells)
    }

    fun isFull(): Boolean {
        return cells.all { row -> row.all { cell -> cell != CellState.EMPTY } }
    }

    fun getCell(row: Int, col: Int): CellState {
        return cells.getOrElse(row) { emptyList() }.getOrElse(col) { CellState.EMPTY }
    }
}
