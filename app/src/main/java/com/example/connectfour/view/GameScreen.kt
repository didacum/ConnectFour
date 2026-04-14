package com.example.connectfour.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectfour.model.Board
import com.example.connectfour.model.GameState
import com.example.connectfour.model.Player
import com.example.connectfour.ui.theme.BackgroundDark
import com.example.connectfour.ui.theme.ConnectFourTheme
import com.example.connectfour.ui.theme.TextPrimary
import com.example.connectfour.viewmodel.GameViewModel
import com.example.connectfour.viewmodel.UiState

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    GameScreenContent(
        uiState = uiState,
        onDropPiece = { column -> viewModel.dropPiece(column) },
        onResetGame = { viewModel.resetGame() },
        onResetScores = { viewModel.resetScores() },
        modifier = modifier
    )
}

@Composable
fun GameScreenContent(
    uiState: UiState,
    onDropPiece: (Int) -> Unit,
    onResetGame: () -> Unit,
    onResetScores: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // Large top spacer — room for camera notch
        Spacer(modifier = Modifier.weight(1.5f))

        // Title
        Text(
            text = "Connect Four",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Score cards
        ScoreView(
            redWins = uiState.redWins,
            yellowWins = uiState.yellowWins,
            currentPlayer = uiState.currentPlayer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Turn indicator
        TurnIndicatorView(
            currentPlayer = uiState.currentPlayer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        // Moderate spacer between turn indicator and board
        Spacer(modifier = Modifier.weight(1f))

        // Board
        BoardView(
            board = uiState.board,
            onColumnClick = onDropPiece,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        // Small spacer between board and buttons — keeps them close
        Spacer(modifier = Modifier.weight(0.6f))

        // Controls
        ControlsView(
            onRestart = onResetGame,
            onResetScores = onResetScores,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Winner dialog — shown on top when game ends
    if (uiState.gameState != GameState.PLAYING) {
        WinnerDialogView(
            gameState = uiState.gameState,
            winner = uiState.winner,
            onDismiss = onResetGame
        )
    }
}

@Preview(name = "GameScreen — Initial")
@Composable
private fun GameScreenPreview1() {
    ConnectFourTheme {
        GameScreenContent(
            uiState = UiState.initial(),
            onDropPiece = {},
            onResetGame = {},
            onResetScores = {}
        )
    }
}

@Preview(name = "GameScreen — Mid game")
@Composable
private fun GameScreenPreview2() {
    ConnectFourTheme {
        val board = Board.empty()
            .dropPiece(0, Player.RED)
            .dropPiece(1, Player.YELLOW)
            .dropPiece(1, Player.RED)
            .dropPiece(2, Player.YELLOW)
            .dropPiece(2, Player.RED)
            .dropPiece(2, Player.YELLOW)
            .dropPiece(3, Player.RED)
            .dropPiece(3, Player.YELLOW)
            .dropPiece(3, Player.RED)
            .dropPiece(4, Player.YELLOW)

        val uiState = UiState(
            board = board,
            currentPlayer = Player.RED,
            gameState = GameState.PLAYING,
            winner = null,
            nextFirstPlayer = Player.YELLOW,
            redWins = 2,
            yellowWins = 1,
            gamesPlayed = 3
        )

        GameScreenContent(
            uiState = uiState,
            onDropPiece = {},
            onResetGame = {},
            onResetScores = {}
        )
    }
}