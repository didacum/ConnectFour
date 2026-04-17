package com.example.connectfour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.connectfour.ui.theme.ConnectFourTheme
import com.example.connectfour.view.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectFourTheme {
                GameScreen()
            }
        }
    }
}
