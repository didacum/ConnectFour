package com.example.connectfour.viewmodel

import com.example.connectfour.model.Board
import com.example.connectfour.model.GameState
import com.example.connectfour.model.Player

data class UiState(
    val board: Board,
    val currentPlayer: Player,
    val gameState: GameState,
    val winner: Player?,
    val nextFirstPlayer: Player,
    val redWins: Int,
    val yellowWins: Int,
    val gamesPlayed: Int
) {
    companion object {
        fun initial(): UiState = UiState(
            board = Board.empty(),
            currentPlayer = Player.RED,
            gameState = GameState.PLAYING,
            winner = null,
            nextFirstPlayer = Player.YELLOW,
            redWins = 0,
            yellowWins = 0,
            gamesPlayed = 0
        )
    }
}
