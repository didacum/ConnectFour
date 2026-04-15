package com.example.connectfour.model

import org.junit.Test
import org.junit.Assert.*

class ScoreTrackerTest {

    private val tracker = ScoreTracker()

    // ==================== INITIAL STATE ====================

    @Test
    fun initialState_redScoreStartsAtZero() {
        assertEquals(0, tracker.getScore(Player.RED))
    }

    @Test
    fun initialState_yellowScoreStartsAtZero() {
        assertEquals(0, tracker.getScore(Player.YELLOW))
    }

    @Test
    fun initialState_gamesPlayedStartsAtZero() {
        assertEquals(0, tracker.getGamesPlayed())
    }

    // ==================== RECORD WIN ====================

    @Test
    fun recordWin_redWins_incrementsRedScoreByOne() {
        tracker.recordWin(Player.RED)
        assertEquals(1, tracker.getScore(Player.RED))
    }

    @Test
    fun recordWin_redWins_yellowScoreRemainsZero() {
        tracker.recordWin(Player.RED)
        assertEquals(0, tracker.getScore(Player.YELLOW))
    }

    @Test
    fun recordWin_redWins_incrementsGamesPlayed() {
        tracker.recordWin(Player.RED)
        assertEquals(1, tracker.getGamesPlayed())
    }

    @Test
    fun recordWin_yellowWins_incrementsYellowScoreByOne() {
        tracker.recordWin(Player.YELLOW)
        assertEquals(1, tracker.getScore(Player.YELLOW))
    }

    @Test
    fun recordWin_yellowWins_redScoreRemainsZero() {
        tracker.recordWin(Player.YELLOW)
        assertEquals(0, tracker.getScore(Player.RED))
    }

    @Test
    fun recordWin_yellowWins_incrementsGamesPlayed() {
        tracker.recordWin(Player.YELLOW)
        assertEquals(1, tracker.getGamesPlayed())
    }

    @Test
    fun recordWin_multipleRedWins_redScoreEqualsThree() {
        tracker.recordWin(Player.RED)
        tracker.recordWin(Player.RED)
        tracker.recordWin(Player.RED)
        assertEquals(3, tracker.getScore(Player.RED))
    }

    @Test
    fun recordWin_mixedWins_correctScores() {
        // RED wins
        tracker.recordWin(Player.RED)
        // YELLOW wins
        tracker.recordWin(Player.YELLOW)
        // RED wins again
        tracker.recordWin(Player.RED)

        assertEquals(2, tracker.getScore(Player.RED))
        assertEquals(1, tracker.getScore(Player.YELLOW))
    }

    @Test
    fun recordWin_mixedWins_gamesPlayedEqualsThree() {
        tracker.recordWin(Player.RED)
        tracker.recordWin(Player.YELLOW)
        tracker.recordWin(Player.RED)

        assertEquals(3, tracker.getGamesPlayed())
    }

    // ==================== RESET SCORES ====================

    @Test
    fun resetScores_afterRedWins_setsRedScoreToZero() {
        tracker.recordWin(Player.RED)
        tracker.recordWin(Player.RED)
        tracker.resetScores()
        assertEquals(0, tracker.getScore(Player.RED))
    }

    @Test
    fun resetScores_afterRedWins_setsYellowScoreToZero() {
        tracker.recordWin(Player.RED)
        tracker.resetScores()
        assertEquals(0, tracker.getScore(Player.YELLOW))
    }

    @Test
    fun resetScores_afterRedWins_setsGamesPlayedToZero() {
        tracker.recordWin(Player.RED)
        tracker.resetScores()
        assertEquals(0, tracker.getGamesPlayed())
    }

    @Test
    fun resetScores_freshTracker_allZeros() {
        // resetScores on a fresh tracker (all zeros)
        tracker.resetScores()
        assertEquals(0, tracker.getScore(Player.RED))
        assertEquals(0, tracker.getScore(Player.YELLOW))
        assertEquals(0, tracker.getGamesPlayed())
    }

    @Test
    fun resetScores_afterReset_recordWinWorksCorrectly() {
        // First record a win
        tracker.recordWin(Player.RED)
        // Then reset
        tracker.resetScores()
        // Record another win
        tracker.recordWin(Player.YELLOW)

        assertEquals(0, tracker.getScore(Player.RED))
        assertEquals(1, tracker.getScore(Player.YELLOW))
        assertEquals(1, tracker.getGamesPlayed())
    }
}