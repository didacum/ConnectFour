package com.example.connectfour.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connectfour.model.Board
import com.example.connectfour.model.CellState
import com.example.connectfour.ui.theme.BoardBlue
import com.example.connectfour.ui.theme.CellEmpty
import com.example.connectfour.ui.theme.PlayerRed
import com.example.connectfour.ui.theme.PlayerYellow

@Composable
fun BoardView(
    board: Board,
    onColumnClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(BoardBlue)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 7 columns
            for (colIndex in 0 until Board.COLUMNS) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onColumnClick(colIndex)
                        },
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // Render rows top to bottom (row 0 at top)
                    for (rowIndex in 0 until Board.ROWS) {
                        val cellState = board.getCell(rowIndex, colIndex)
                        val cellColor = when (cellState) {
                            CellState.EMPTY -> CellEmpty
                            CellState.RED -> PlayerRed
                            CellState.YELLOW -> PlayerYellow
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .background(cellColor)
                        )
                    }
                }
            }
        }
    }
}