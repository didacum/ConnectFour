package com.example.connectfour.model

import org.junit.Test
import org.junit.Assert.*

class BoardTest {

    // ===== Board.empty() =====

    @Test
    fun empty_returns7x6Board() {
        val board = Board.empty()

        assertEquals(6, board.cells.size) // 6 rows

        for (row in board.cells) {
            assertEquals(7, row.size) // 7 columns per row
        }
    }

    @Test
    fun empty_allCellsAreEmpty() {
        val board = Board.empty()

        for (row in 0 until Board.ROWS) {
            for (col in 0 until Board.COLUMNS) {
                assertEquals(CellState.EMPTY, board.getCell(row, col))
            }
        }
    }

    @Test
    fun empty_boardIsNotFull() {
        val board = Board.empty()

        assertFalse(board.isFull())
    }

    // ===== Board dimensions =====

    @Test
    fun columns_equals7() {
        assertEquals(7, Board.COLUMNS)
    }

    @Test
    fun rows_equals6() {
        assertEquals(6, Board.ROWS)
    }

    // ===== dropPiece — gravity =====

    @Test
    fun dropPiece_redIntoEmptyColumn_appearsAtBottomRow() {
        val board = Board.empty()
        val newBoard = board.dropPiece(0, Player.RED)

        // Token should be at row 5 (bottom row, highest index)
        assertEquals(CellState.RED, newBoard.getCell(5, 0))

        // All other cells should be empty
        for (row in 0 until Board.ROWS - 1) {
            assertEquals(CellState.EMPTY, newBoard.getCell(row, 0))
        }
    }

    @Test
    fun dropPiece_yellowIntoEmptyColumn_appearsAtBottomRow() {
        val board = Board.empty()
        val newBoard = board.dropPiece(0, Player.YELLOW)

        assertEquals(CellState.YELLOW, newBoard.getCell(5, 0))
    }

    @Test
    fun dropPiece_intoColumnWithOneToken_appearsAbove() {
        val board = Board.empty().dropPiece(0, Player.RED) // row 5
        val newBoard = board.dropPiece(0, Player.YELLOW)   // row 4

        assertEquals(CellState.RED, newBoard.getCell(5, 0))
        assertEquals(CellState.YELLOW, newBoard.getCell(4, 0))
    }

    @Test
    fun dropPiece_intoColumnWithFiveTokens_appearsAtTop() {
        var board = Board.empty()
        // Fill 5 rows (rows 5, 4, 3, 2, 1)
        board = board.dropPiece(0, Player.RED)   // row 5
        board = board.dropPiece(0, Player.YELLOW) // row 4
        board = board.dropPiece(0, Player.RED)   // row 3
        board = board.dropPiece(0, Player.YELLOW) // row 2
        board = board.dropPiece(0, Player.RED)   // row 1

        // Next drop should be at row 0
        val newBoard = board.dropPiece(0, Player.YELLOW)

        assertEquals(CellState.YELLOW, newBoard.getCell(0, 0))
    }

    @Test
    fun dropPiece_doesNotMutateOriginalBoard() {
        val original = Board.empty()
        original.dropPiece(0, Player.RED)

        // Original should still be empty
        assertEquals(CellState.EMPTY, original.getCell(5, 0))
    }

    @Test
    fun dropPiece_returnsNewBoardInstance() {
        val original = Board.empty()
        val newBoard = original.dropPiece(0, Player.RED)

        assertNotSame(original, newBoard)
    }

    // ===== dropPiece — full column no-op =====

    @Test
    fun dropPiece_fullColumn_seventhDropReturnsSameBoard() {
        var board = Board.empty()
        // Fill column with 6 tokens (all rows)
        for (i in 0 until Board.ROWS) {
            board = board.dropPiece(0, if (i % 2 == 0) Player.RED else Player.YELLOW)
        }

        // 7th drop should return the same board (no change)
        val result = board.dropPiece(0, Player.RED)

        assertSame(board, result)
    }

    @Test
    fun dropPiece_fullColumn_unaffectedAfterNoOpDrop() {
        var board = Board.empty()
        for (i in 0 until Board.ROWS) {
            board = board.dropPiece(0, if (i % 2 == 0) Player.RED else Player.YELLOW)
        }

        // Store state before no-op drop
        val prevCell = board.getCell(5, 0)

        board.dropPiece(0, Player.RED) // no-op

        assertEquals(prevCell, board.getCell(5, 0))
    }

    @Test
    fun isFull_withOneFullColumn_returnsFalse() {
        var board = Board.empty()
        for (i in 0 until Board.ROWS) {
            board = board.dropPiece(0, if (i % 2 == 0) Player.RED else Player.YELLOW)
        }

        assertFalse(board.isFull())
    }

    // ===== dropPiece — bounds =====

    @Test
    fun dropPiece_column0_leftmost_works() {
        val board = Board.empty()
        val newBoard = board.dropPiece(0, Player.RED)

        assertEquals(CellState.RED, newBoard.getCell(5, 0))
    }

    @Test
    fun dropPiece_column6_rightmost_works() {
        val board = Board.empty()
        val newBoard = board.dropPiece(6, Player.RED)

        assertEquals(CellState.RED, newBoard.getCell(5, 6))
    }

    @Test
    fun dropPiece_invalidColumnNegative1_returnsUnchangedBoard() {
        val board = Board.empty()
        val newBoard = board.dropPiece(-1, Player.RED)

        assertSame(board, newBoard)
    }

    @Test
    fun dropPiece_invalidColumn7_returnsUnchangedBoard() {
        val board = Board.empty()
        val newBoard = board.dropPiece(7, Player.RED)

        assertSame(board, newBoard)
    }

    @Test
    fun dropPiece_invalidColumn_boardUnchanged() {
        val board = Board.empty()

        board.dropPiece(-1, Player.RED)
        board.dropPiece(7, Player.RED)

        // All cells should still be empty
        for (row in 0 until Board.ROWS) {
            for (col in 0 until Board.COLUMNS) {
                assertEquals(CellState.EMPTY, board.getCell(row, col))
            }
        }
    }

    // ===== isFull =====

    @Test
    fun isFull_emptyBoard_returnsFalse() {
        val board = Board.empty()

        assertFalse(board.isFull())
    }

    @Test
    fun isFull_partiallyFilled_returnsFalse() {
        val board = Board.empty()
            .dropPiece(0, Player.RED)
            .dropPiece(1, Player.YELLOW)

        assertFalse(board.isFull())
    }

    @Test
    fun isFull_all42CellsFilled_returnsTrue() {
        var board = Board.empty()

        // Fill all columns
        for (col in 0 until Board.COLUMNS) {
            for (row in 0 until Board.ROWS) {
                board = board.dropPiece(col, if ((col + row) % 2 == 0) Player.RED else Player.YELLOW)
            }
        }

        assertTrue(board.isFull())
    }

    // ===== getCell =====

    @Test
    fun getCell_emptyBoard_returnsEmptyForAnyValidCell() {
        val board = Board.empty()

        for (row in 0 until Board.ROWS) {
            for (col in 0 until Board.COLUMNS) {
                assertEquals(CellState.EMPTY, board.getCell(row, col))
            }
        }
    }

    @Test
    fun getCell_afterDrop_returnsCorrectCellState() {
        val board = Board.empty().dropPiece(3, Player.RED)

        assertEquals(CellState.RED, board.getCell(5, 3))
    }

    @Test
    fun getCell_outOfBoundsRow_returnsEmpty() {
        val board = Board.empty()

        // Invalid row indices
        assertEquals(CellState.EMPTY, board.getCell(-1, 0))
        assertEquals(CellState.EMPTY, board.getCell(6, 0))
    }

    @Test
    fun getCell_outOfBoundsCol_returnsEmpty() {
        val board = Board.empty()

        // Invalid column indices
        assertEquals(CellState.EMPTY, board.getCell(0, -1))
        assertEquals(CellState.EMPTY, board.getCell(0, 7))
    }
}