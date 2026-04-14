package com.example.connectfour.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectfour.ui.theme.ButtonPrimary
import com.example.connectfour.ui.theme.ButtonSecondary
import com.example.connectfour.ui.theme.ButtonSecondaryBorder
import com.example.connectfour.ui.theme.TextPrimary

@Composable
fun ControlsView(
    onRestart: () -> Unit,
    onResetScores: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Button(
            onClick = onRestart,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonPrimary
            )
        ) {
            Text(
                text = "REINICIAR PARTIDA",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        OutlinedButton(
            onClick = onResetScores,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, ButtonSecondaryBorder),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = ButtonSecondary
            )
        ) {
            Text(
                text = "RESETEAR MARCADOR",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}