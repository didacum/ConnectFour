package com.example.connectfour.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.connectfour.model.Player
import com.example.connectfour.ui.theme.ConnectFourTheme
import com.example.connectfour.ui.theme.PlayerRed
import com.example.connectfour.ui.theme.PlayerYellow
import com.example.connectfour.ui.theme.TextPrimary

@Composable
fun TurnIndicatorView(
    currentPlayer: Player,
    modifier: Modifier = Modifier
) {
    val indicatorColor = when (currentPlayer) {
        Player.RED -> PlayerRed
        Player.YELLOW -> PlayerYellow
    }

    val textColor = when (currentPlayer) {
        Player.RED -> PlayerRed
        Player.YELLOW -> PlayerYellow
    }

    Row(
        modifier = modifier.padding(start = 24.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(indicatorColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "ACTIVE TURN",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Preview(name = "TurnIndicator — Red turn")
@Composable
private fun TurnIndicatorViewPreview1() {
    ConnectFourTheme {
        TurnIndicatorView(currentPlayer = Player.RED)
    }
}

@Preview(name = "TurnIndicator — Yellow turn")
@Composable
private fun TurnIndicatorViewPreview2() {
    ConnectFourTheme {
        TurnIndicatorView(currentPlayer = Player.YELLOW)
    }
}