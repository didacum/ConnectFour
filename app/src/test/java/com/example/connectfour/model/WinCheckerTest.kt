package com.example.connectfour.model

import org.junit.Test
import org.junit.Assert.*

class WinCheckerTest {

    private val checker = ConnectFourWinChecker()

    private fun buildBoard(vararg rows: String): Board {
        require(rows.size == Board.ROWS) { "Must provide exactly ${Board.ROWS} rows" }
        require(rows.all { it.length == Board.COLUMNS }) { "Each row must have exactly ${Board.COLUMNS} columns" }
        
        val cells = rows.map { row ->
            row.map { char ->
                when (char) {
                    'R' -> CellState.RED
                    'Y' -> CellState.YELLOW
                    '.' -> CellState.EMPTY
                    else -> throw IllegalArgumentException("Invalid character: $char")
                }
            }
        }
        return Board(cells)
    }

    // ==================== HORIZONTAL WINS ====================

    @Test
    fun checkHorizontalWin_redFourInRow_returnsTrue() {
        // Row 5 (bottom) has RRRR
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "RRRR..."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkHorizontalWin_yellowFourInRow_returnsTrue() {
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "YYYY..."
        )
        assertTrue(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkHorizontalWin_threeConsecutive_returnsFalse() {
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "RRR...."
        )
        assertFalse(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkHorizontalWin_fourTokensInterrupted_returnsFalse() {
        // RED, RED, YELLOW, RED - not consecutive
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "RRYR..."
        )
        assertFalse(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkHorizontalWin_atLastColumn_col6_returnsTrue() {
        // Win at column 6 (0-indexed)
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "...RRRR"
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    // ==================== VERTICAL WINS ====================

    @Test
    fun checkVerticalWin_fourConsecutive_returnsTrue() {
        val board = buildBoard(
            ".......",
            ".......",
            "..R....",
            "..R....",
            "..R....",
            "..R...."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkVerticalWin_yellowFourInColumn_returnsTrue() {
        val board = buildBoard(
            ".......",
            "..Y....",
            "..Y....",
            "..Y....",
            "..Y....",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkVerticalWin_threeTokens_returnsFalse() {
        val board = buildBoard(
            ".......",
            ".......",
            "..R....",
            "..R....",
            "..R....",
            "......."
        )
        assertFalse(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkVerticalWin_atLastRow_row5_returnsTrue() {
        // Win at the very last row (row 5, 0-indexed) - using horizontal
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "RRRR..."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    // ==================== DIAGONAL WINS ↘ (top-left to bottom-right) ====================

    @Test
    fun checkDiagonalDownRight_win_returnsTrue() {
        // Diagonal: row0col0, row1col1, row2col2, row3col3
        val board = buildBoard(
            "R......",
            ".R.....",
            "..R....",
            "...R...",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkDiagonalDownRight_yellow_returnsTrue() {
        val board = buildBoard(
            "Y......",
            ".Y.....",
            "..Y....",
            "...Y...",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkDiagonalDownRight_fromBottomLeft_returnsTrue() {
        // Diagonal ↘ from row2col4, row3col3, row4col2, row5col1
        val board = buildBoard(
            ".......",
            ".......",
            "....R..", // row2 col4
            "...R...", // row3 col3
            "..R....", // row4 col2
            ".R....."  // row5 col1 - FIXED: was .R...... (8 chars)
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkDiagonalDownRight_fromTopRight_returnsTrue() {
        // Diagonal: row0col6, row1col5, row2col4, row3col3
        val board = buildBoard(
            ".....R.",
            "....R..",
            "...R...",
            "..R....",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    // ==================== DIAGONAL WINS ↙ (top-right to bottom-left) ====================

    @Test
    fun checkDiagonalDownLeft_win_returnsTrue() {
        // Diagonal: row0col3, row1col2, row2col1, row3col0
        val board = buildBoard(
            "...R...",
            "..R....",
            ".R.....",
            "R......",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkDiagonalDownLeft_yellow_returnsTrue() {
        val board = buildBoard(
            "...Y...",
            "..Y....",
            ".Y.....",
            "Y......",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkDiagonalDownLeft_fromBottomRight_returnsTrue() {
        // Diagonal ↙ from row0col6, row1col5, row2col4, row3col3
        val board = buildBoard(
            ".....Y.", // row0 col5
            "....Y..", // row1 col4
            "...Y...", // row2 col3
            "..Y....", // row3 col2
            ".Y.....", // row4 col1
            "Y......"  // row5 col0
        )
        assertTrue(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkDiagonalDownLeft_fromTopLeft_returnsTrue() {
        // Diagonal: row0col0, row1col1, row2col2, row3col3 (this is actually ↘)
        // For ↙: row0col3, row1col2, row2col1, row3col0
        val board = buildBoard(
            "..R....",
            "...R...",
            "....R..",
            ".....R.",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    // ==================== EDGE CASES ====================

    @Test
    fun checkEmptyBoard_red_returnsFalse() {
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "......."
        )
        assertFalse(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkEmptyBoard_yellow_returnsFalse() {
        val board = Board.empty()
        assertFalse(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkFullBoardNoWinner_returnsFalse() {
        // Full board with no 4-in-a-row - using offset pattern
        val board = buildBoard(
            "RRYYRRY",
            "YYRRYYR",
            "RRYYRRY",
            "YYRRYYR",
            "RRYYRRY",
            "YYRRYYR"
        )
        assertFalse(checker.checkWin(board, Player.RED))
        assertFalse(checker.checkWin(board, Player.YELLOW))
    }

    @Test
    fun checkWinAtLastRow_row5_returnsTrue() {
        // Win at the very last row (row 5, 0-indexed)
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "RRRR..."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkWinAtLastColumn_col6_returnsTrue() {
        // Win at the very last column (col 6, 0-indexed)
        val board = buildBoard(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "...RRRR"
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkWin_spanningBoardBoundaries_returnsTrue() {
        // Diagonal from near edge to edge
        val board = buildBoard(
            "......R",
            ".....R.",
            "....R..",
            "...R...",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkNoWin_withGapsBetweenFour_returnsFalse() {
        // 4 tokens but with gaps - should NOT be a win
        val board = buildBoard(
            ".......",
            ".......",
            "..R.R..",
            ".......",
            ".......",
            "......."
        )
        assertFalse(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkVerticalWin_topRows_returnsTrue() {
        // Win at top of board
        val board = buildBoard(
            "..R....",
            "..R....",
            "..R....",
            "..R....",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }

    @Test
    fun checkHorizontalWin_middleRow_returnsTrue() {
        // Win in middle of board
        val board = buildBoard(
            ".......",
            ".......",
            "RRRR...",
            ".......",
            ".......",
            "......."
        )
        assertTrue(checker.checkWin(board, Player.RED))
    }
}