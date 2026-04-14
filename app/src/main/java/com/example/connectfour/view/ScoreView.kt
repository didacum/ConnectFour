package com.example.connectfour.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.connectfour.model.Player
import com.example.connectfour.ui.theme.ConnectFourTheme
import com.example.connectfour.ui.theme.PlayerRed
import com.example.connectfour.ui.theme.PlayerYellow
import com.example.connectfour.ui.theme.ScoreCardAccentRed
import com.example.connectfour.ui.theme.ScoreCardAccentYellow
import com.example.connectfour.ui.theme.ScoreCardBackground
import com.example.connectfour.ui.theme.TextPrimary
import com.example.connectfour.ui.theme.TextSecondary

@Composable
fun ScoreView(
    redWins: Int,
    yellowWins: Int,
    currentPlayer: Player,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PlayerScoreCard(
            label = "PLAYER 1",
            wins = redWins,
            isActive = currentPlayer == Player.RED,
            accentColor = ScoreCardAccentRed,
            modifier = Modifier.weight(1f)
        )
        PlayerScoreCard(
            label = "PLAYER 2",
            wins = yellowWins,
            isActive = currentPlayer == Player.YELLOW,
            accentColor = ScoreCardAccentYellow,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PlayerScoreCard(
    label: String,
    wins: Int,
    isActive: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val borderModifier = if (isActive) {
        Modifier.border(
            width = 3.dp,
            color = accentColor,
            shape = RoundedCornerShape(16.dp)
        )
    } else {
        Modifier
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .then(borderModifier)
            .background(ScoreCardBackground)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary
        )
        Text(
            text = String.format("%02d", wins),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

@Preview(name = "ScoreView — Player 1 active")
@Composable
private fun ScoreViewPreview1() {
    ConnectFourTheme {
        ScoreView(
            redWins = 12,
            yellowWins = 8,
            currentPlayer = Player.RED
        )
    }
}

@Preview(name = "ScoreView — Player 2 active")
@Composable
private fun ScoreViewPreview2() {
    ConnectFourTheme {
        ScoreView(
            redWins = 3,
            yellowWins = 7,
            currentPlayer = Player.YELLOW
        )
    }
}