package com.example.connectfour.model

interface WinChecker {
    fun checkWin(board: Board, player: Player): Boolean
}
