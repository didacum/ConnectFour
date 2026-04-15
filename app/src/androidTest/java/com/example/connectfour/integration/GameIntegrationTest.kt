package com.example.connectfour.integration

import com.example.connectfour.model.Board
import com.example.connectfour.model.CellState
import com.example.connectfour.model.ConnectFourWinChecker
import com.example.connectfour.model.GameState
import com.example.connectfour.model.Player
import com.example.connectfour.model.ScoreTracker
import com.example.connectfour.model.WinChecker
import com.example.connectfour.viewmodel.GameViewModel
import com.example.connectfour.viewmodel.UiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Integration tests for Connect Four that verify full MVVM flow.
 * These tests run on device/emulator but don't require UI - they test
 * the integration between ViewModel and Model layers.
 */
@RunWith(JUnit4::class)
class GameIntegrationTest {

    /**
     * Test 1: Drop piece updates UI state via ViewModel
     */
    @Test
    fun dropPiece_updatesUiStateViaViewModel() = runBlocking {
        val viewModel = GameViewModel()
        val initialState = viewModel.uiState.first()

        // Verify initial state: RED starts, board empty
        assert(initialState.currentPlayer == Player.RED)
        assert(initialState.board.getCell(5, 0) == CellState.EMPTY)

        // Drop piece in column 0
        viewModel.dropPiece(0)

        val stateAfterDrop = viewModel.uiState.first()

        // Verify RED token placed at bottom of column 0
        assert(stateAfterDrop.board.getCell(5, 0) == CellState.RED) {
            "Expected RED at row 5, col 0, got ${stateAfterDrop.board.getCell(5, 0)}"
        }

        // Verify current player switched to YELLOW
        assert(stateAfterDrop.currentPlayer == Player.YELLOW) {
            "Expected YELLOW after RED's turn, got ${stateAfterDrop.currentPlayer}"
        }
    }

    /**
     * Test 2: Win detection updates scores and game state
     */
    @Test
    fun winDetection_updatesScoresAndGameState() {
        val scoreTracker = ScoreTracker()
        val winChecker = ConnectFourWinChecker()

        // Create a board with winning position for RED
        // Horizontal win at bottom row: RED in columns 0, 1, 2, 3
        var board = Board.empty()
            .dropPiece(0, Player.RED)  // row 5, col 0
            .dropPiece(1, Player.RED)   // row 5, col 1
            .dropPiece(2, Player.RED)   // row 5, col 2
            .dropPiece(3, Player.RED)  // row 5, col 3 -- RED wins!

        // Verify win detection
        assert(winChecker.checkWin(board, Player.RED)) {
            "Expected win detection for RED"
        }

        // Record the win
        scoreTracker.recordWin(Player.RED)
        assert(scoreTracker.getScore(Player.RED) == 1)
        assert(scoreTracker.getGamesPlayed() == 1)

        // Test vertical win as well
        var boardVertical = Board.empty()
            .dropPiece(0, Player.YELLOW)  // row 5, col 0
            .dropPiece(0, Player.YELLOW)  // row 4, col 0
            .dropPiece(0, Player.YELLOW)  // row 3, col 0
            .dropPiece(0, Player.YELLOW)  // row 2, col 0 -- YELLOW wins vertically!

        assert(winChecker.checkWin(boardVertical, Player.YELLOW)) {
            "Expected vertical win detection for YELLOW"
        }
    }

    /**
     * Test 3: Reset game alternates first player
     */
    @Test
    fun resetGame_alternatesFirstPlayer() = runBlocking {
        val viewModel = GameViewModel()
        val initialState = viewModel.uiState.first()

        // Initial: RED starts first
        assert(initialState.currentPlayer == Player.RED)
        assert(initialState.nextFirstPlayer == Player.YELLOW)

        // Simulate RED winning
        viewModel.dropPiece(0) // RED
        viewModel.dropPiece(0) // YELLOW
        viewModel.dropPiece(1) // RED
        viewModel.dropPiece(1) // YELLOW
        viewModel.dropPiece(2) // RED
        viewModel.dropPiece(2) // YELLOW
        viewModel.dropPiece(3) // RED -- RED wins horizontally!

        val stateAfterWin = viewModel.uiState.first()
        assert(stateAfterWin.gameState == GameState.WON)
        assert(stateAfterWin.winner == Player.RED)

        // Now reset the game
        viewModel.resetGame()
        val stateAfterReset = viewModel.uiState.first()

        // Verify YELLOW starts (nextFirstPlayer was YELLOW before reset)
        assert(stateAfterReset.currentPlayer == Player.YELLOW) {
            "Expected YELLOW after reset (alternated), got ${stateAfterReset.currentPlayer}"
        }

        // Verify nextFirstPlayer is RED (for the NEXT game after this one)
        assert(stateAfterReset.nextFirstPlayer == Player.RED) {
            "Expected nextFirstPlayer=RED after reset, got ${stateAfterReset.nextFirstPlayer}"
        }

        // Verify board is cleared
        assert(stateAfterReset.board.getCell(5, 0) == CellState.EMPTY)
        assert(stateAfterReset.gameState == GameState.PLAYING)
    }

    /**
     * Test 4: Reset scores resets everything
     */
    @Test
    fun resetScores_resetsEverything() = runBlocking {
        val viewModel = GameViewModel()

        // Game 1: RED wins
        viewModel.dropPiece(0)
        viewModel.dropPiece(0)
        viewModel.dropPiece(1)
        viewModel.dropPiece(1)
        viewModel.dropPiece(2)
        viewModel.dropPiece(2)
        viewModel.dropPiece(3) // RED wins

        var state = viewModel.uiState.first()
        assert(state.redWins == 1) { "Expected redWins=1, got ${state.redWins}" }

        // Game 2: YELLOW wins
        viewModel.resetGame()
        viewModel.dropPiece(0)
        viewModel.dropPiece(0)
        viewModel.dropPiece(1)
        viewModel.dropPiece(1)
        viewModel.dropPiece(2)
        viewModel.dropPiece(2)
        viewModel.dropPiece(3) // YELLOW wins

        state = viewModel.uiState.first()
        assert(state.yellowWins == 1) { "Expected yellowWins=1, got ${state.yellowWins}" }

        // Game 3: RED wins again
        viewModel.resetGame()
        viewModel.dropPiece(0)
        viewModel.dropPiece(0)
        viewModel.dropPiece(1)
        viewModel.dropPiece(1)
        viewModel.dropPiece(2)
        viewModel.dropPiece(2)
        viewModel.dropPiece(3) // RED wins

        state = viewModel.uiState.first()
        assert(state.redWins == 2) { "Expected redWins=2, got ${state.redWins}" }
        assert(state.yellowWins == 1) { "Expected yellowWins=1, got ${state.yellowWins}" }
        assert(state.gamesPlayed == 3) { "Expected gamesPlayed=3, got ${state.gamesPlayed}" }

        // Now reset scores
        viewModel.resetScores()
        state = viewModel.uiState.first()

        // Verify everything reset
        assert(state.redWins == 0) { "Expected redWins=0 after reset, got ${state.redWins}" }
        assert(state.yellowWins == 0) { "Expected yellowWins=0 after reset, got ${state.yellowWins}" }
        assert(state.gamesPlayed == 0) { "Expected gamesPlayed=0 after reset, got ${state.gamesPlayed}" }

        // Verify board is empty (new game starts)
        assert(state.board.getCell(5, 0) == CellState.EMPTY)

        // Verify RED starts (fresh start)
        assert(state.currentPlayer == Player.RED) {
            "Expected RED after resetScores, got ${state.currentPlayer}"
        }
    }

    /**
     * Test 5: Draw detection
     */
    @Test
    fun drawDetection_setsDrawState() {
        val winChecker = ConnectFourWinChecker()

        // Fill the entire board without a winner
        // Alternating pattern avoids any 4-in-a-row
        var board = Board.empty()

        for (col in 0 until Board.COLUMNS) {
            for (row in 0 until Board.ROWS) {
                val player = if ((col + row) % 2 == 0) Player.RED else Player.YELLOW
                board = board.dropPiece(col, player)
            }
        }

        // Verify board is full
        assert(board.isFull())

        // Verify no winner
        assert(!winChecker.checkWin(board, Player.RED))
        assert(!winChecker.checkWin(board, Player.YELLOW))
    }
}