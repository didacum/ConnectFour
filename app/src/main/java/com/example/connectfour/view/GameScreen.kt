package com.example.connectfour.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectfour.model.GameState
import com.example.connectfour.ui.theme.BackgroundDark
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
        onColumnClick = { column -> viewModel.dropPiece(column) },
        onRestart = { viewModel.resetGame() },
        onResetScores = { viewModel.resetScores() },
        modifier = modifier
    )
}

@Composable
private fun GameScreenContent(
    uiState: UiState,
    onColumnClick: (Int) -> Unit,
    onRestart: () -> Unit,
    onResetScores: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(top = 24.dp)
    ) {
        // Title
        Text(
            text = "Connect Four",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // Score row
        ScoreView(
            redWins = uiState.redWins,
            yellowWins = uiState.yellowWins,
            currentPlayer = uiState.currentPlayer
        )

        // Turn indicator
        TurnIndicatorView(
            currentPlayer = uiState.currentPlayer
        )

        // Spacer to push board to center/lower area
        Spacer(modifier = Modifier.weight(1f, fill = true))

        // Board
        BoardView(
            board = uiState.board,
            onColumnClick = onColumnClick
        )

        // Spacer
        Spacer(modifier = Modifier.weight(1f, fill = true))

        // Controls at bottom
        ControlsView(
            onRestart = onRestart,
            onResetScores = onResetScores,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Winner dialog ( conditionally shown)
        if (uiState.gameState != GameState.PLAYING) {
            WinnerDialogView(
                gameState = uiState.gameState,
                winner = uiState.winner,
                onDismiss = onRestart
            )
        }
    }
}