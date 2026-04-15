package com.example.connectfour

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.connectfour.model.GameState
import com.example.connectfour.model.Player
import com.example.connectfour.ui.theme.ConnectFourTheme
import com.example.connectfour.view.ControlsView
import com.example.connectfour.view.GameScreenContent
import com.example.connectfour.view.WinnerDialogView
import com.example.connectfour.viewmodel.UiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Test 1: Board is displayed with 42 cells
    @Test
    fun boardDisplays42Cells() {
        composeTestRule.setContent {
            ConnectFourTheme {
                GameScreenContent(
                    uiState = UiState.initial(),
                    onDropPiece = {},
                    onResetGame = {},
                    onResetScores = {}
                )
            }
        }
        // Verify title is shown
        composeTestRule.onNodeWithText("Connect Four").assertIsDisplayed()
        // Verify score labels
        composeTestRule.onNodeWithText("PLAYER 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("PLAYER 2").assertIsDisplayed()
        // Verify buttons
        composeTestRule.onNodeWithText("REINICIAR PARTIDA").assertIsDisplayed()
        composeTestRule.onNodeWithText("RESETEAR MARCADOR").assertIsDisplayed()
    }

    // Test 2: Tapping restart button triggers callback
    @Test
    fun restartButtonTriggersCallback() {
        var restartCalled = false
        composeTestRule.setContent {
            ConnectFourTheme {
                ControlsView(
                    onRestart = { restartCalled = true },
                    onResetScores = {}
                )
            }
        }
        composeTestRule.onNodeWithText("REINICIAR PARTIDA").performClick()
        assertTrue(restartCalled)
    }

    // Test 3: Reset scores button triggers callback
    @Test
    fun resetScoresButtonTriggersCallback() {
        var resetCalled = false
        composeTestRule.setContent {
            ConnectFourTheme {
                ControlsView(
                    onRestart = {},
                    onResetScores = { resetCalled = true }
                )
            }
        }
        composeTestRule.onNodeWithText("RESETEAR MARCADOR").performClick()
        assertTrue(resetCalled)
    }

    // Test 4: Winner dialog shown when game is WON
    @Test
    fun winnerDialogShownOnWin() {
        composeTestRule.setContent {
            ConnectFourTheme {
                WinnerDialogView(
                    gameState = GameState.WON,
                    winner = Player.RED,
                    onDismiss = {}
                )
            }
        }
        composeTestRule.onNodeWithText("¡Tenemos ganador!").assertIsDisplayed()
    }
}
