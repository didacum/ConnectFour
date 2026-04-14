package com.example.connectfour.model

class ScoreTracker {
    private val scores = mutableMapOf(
        Player.RED to 0,
        Player.YELLOW to 0
    )
    private var gamesPlayed = 0

    fun recordWin(player: Player) {
        scores[player] = scores.getOrDefault(player, 0) + 1
        gamesPlayed++
    }

    fun incrementGamesPlayed() {
        gamesPlayed++
    }

    fun resetScores() {
        scores[Player.RED] = 0
        scores[Player.YELLOW] = 0
        gamesPlayed = 0
    }

    fun getScore(player: Player): Int {
        return scores.getOrDefault(player, 0)
    }

    fun getGamesPlayed(): Int {
        return gamesPlayed
    }
}
