package com.example.connectfour.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.connectfour.model.GameState
import com.example.connectfour.model.Player
import com.example.connectfour.ui.theme.ConnectFourTheme
import com.example.connectfour.ui.theme.ScoreCardBackground
import com.example.connectfour.ui.theme.TextPrimary

@Composable
fun WinnerDialogView(
    gameState: GameState,
    winner: Player?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (gameState == GameState.PLAYING) return

    val title = when (gameState) {
        GameState.WON -> "¡Tenemos ganador!"
        GameState.DRAW -> "¡Empate!"
        else -> return
    }

    val message = when (gameState) {
        GameState.WON -> {
            val playerName = when (winner) {
                Player.RED -> "Player 1"
                Player.YELLOW -> "Player 2"
                else -> "Unknown"
            }
            "$playerName wins!"
        }
        GameState.DRAW -> "Nobody wins this time."
        else -> ""
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = TextPrimary
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "NUEVA PARTIDA",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = ScoreCardBackground,
        titleContentColor = TextPrimary,
        textContentColor = TextPrimary
    )
}

@Preview(name = "WinnerDialog — Player 1 wins")
@Composable
private fun WinnerDialogViewPreview1() {
    ConnectFourTheme {
        WinnerDialogView(
            gameState = GameState.WON,
            winner = Player.RED,
            onDismiss = {}
        )
    }
}

@Preview(name = "WinnerDialog — Draw")
@Composable
private fun WinnerDialogViewPreview2() {
    ConnectFourTheme {
        WinnerDialogView(
            gameState = GameState.DRAW,
            winner = null,
            onDismiss = {}
        )
    }
}