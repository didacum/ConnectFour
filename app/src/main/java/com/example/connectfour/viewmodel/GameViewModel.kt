package com.example.connectfour.viewmodel

import androidx.lifecycle.ViewModel
import com.example.connectfour.model.Board
import com.example.connectfour.model.ConnectFourWinChecker
import com.example.connectfour.model.GameState
import com.example.connectfour.model.Player
import com.example.connectfour.model.ScoreTracker
import com.example.connectfour.model.WinChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(
    private val winChecker: WinChecker = ConnectFourWinChecker(),
    private val scoreTracker: ScoreTracker = ScoreTracker()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.initial())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun dropPiece(column: Int) {
        val current = _uiState.value
        
        // No-op if game is not PLAYING
        if (current.gameState != GameState.PLAYING) return

        // Drop the piece (Board.dropPiece handles full column / invalid bounds as no-op)
        val newBoard = current.board.dropPiece(column, current.currentPlayer)
        
        // No-op if board did not change (column was full or invalid)
        if (newBoard == current.board) return

        // Check win
        if (winChecker.checkWin(newBoard, current.currentPlayer)) {
            scoreTracker.recordWin(current.currentPlayer)
            _uiState.value = current.copy(
                board = newBoard,
                gameState = GameState.WON,
                winner = current.currentPlayer,
                redWins = scoreTracker.getScore(Player.RED),
                yellowWins = scoreTracker.getScore(Player.YELLOW),
                gamesPlayed = scoreTracker.getGamesPlayed()
            )
            return
        }

        // Check draw
        if (newBoard.isFull()) {
            // For draw: increment gamesPlayed but no win recorded
            scoreTracker.incrementGamesPlayed()
            _uiState.value = current.copy(
                board = newBoard,
                gameState = GameState.DRAW,
                winner = null,
                redWins = scoreTracker.getScore(Player.RED),
                yellowWins = scoreTracker.getScore(Player.YELLOW),
                gamesPlayed = scoreTracker.getGamesPlayed()
            )
            return
        }

        // Normal move — switch player
        val nextPlayer = if (current.currentPlayer == Player.RED) Player.YELLOW else Player.RED
        _uiState.value = current.copy(
            board = newBoard,
            currentPlayer = nextPlayer
        )
    }

    fun resetGame() {
        val current = _uiState.value
        _uiState.value = current.copy(
            board = Board.empty(),
            currentPlayer = current.nextFirstPlayer,
            gameState = GameState.PLAYING,
            winner = null,
            nextFirstPlayer = if (current.nextFirstPlayer == Player.RED) Player.YELLOW else Player.RED
        )
    }

    fun resetScores() {
        scoreTracker.resetScores()
        _uiState.value = UiState.initial()
    }
}
