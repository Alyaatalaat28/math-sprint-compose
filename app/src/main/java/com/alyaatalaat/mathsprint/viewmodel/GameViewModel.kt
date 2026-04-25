package com.alyaatalaat.mathsprint.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alyaatalaat.mathsprint.model.Difficulty
import com.alyaatalaat.mathsprint.model.Operation
import com.alyaatalaat.mathsprint.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore:
        DataStore<Preferences> by preferencesDataStore(name = "math_sprint_prefs")

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = application.dataStore
    private val BEST_SCORE_KEY = intPreferencesKey("best_score")

    private val _score         = MutableStateFlow(0)
    private val _streak        = MutableStateFlow(0)
    private val _timeLeft      = MutableStateFlow(0)
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    private val _correctCount  = MutableStateFlow(0)
    private val _wrongCount    = MutableStateFlow(0)
    private val _isGameOver    = MutableStateFlow(false)
    private val _bestScore     = MutableStateFlow(0)
    private val _isNewBest     = MutableStateFlow(false)
    private val _combo         = MutableStateFlow(1)

    val score:           StateFlow<Int>       = _score
    val streak:          StateFlow<Int>       = _streak
    val timeLeft:        StateFlow<Int>       = _timeLeft
    val currentQuestion: StateFlow<Question?> = _currentQuestion
    val correctCount:    StateFlow<Int>       = _correctCount
    val wrongCount:      StateFlow<Int>       = _wrongCount
    val isGameOver:      StateFlow<Boolean>   = _isGameOver
    val bestScore:       StateFlow<Int>       = _bestScore
    val isNewBest:       StateFlow<Boolean>   = _isNewBest
    val combo:           StateFlow<Int>       = _combo

    // ── Config ──
    private var difficulty  = Difficulty.EASY
    private var operation   = Operation.ADDITION
    private var timerJob: Job? = null

    // ── Computed ──
    val accuracy: Int get() {
        val total = _correctCount.value + _wrongCount.value
        return if (total == 0) 0 else (_correctCount.value * 100) / total
    }

    init {
        // Load best score from DataStore on start
        viewModelScope.launch {
            dataStore.data.map { prefs ->
                prefs[BEST_SCORE_KEY] ?: 0
            }.collect { saved ->
                _bestScore.value = saved
            }
        }
    }

    // ─────────────────────────────────────────
    // MARK: - Game Control
    // ─────────────────────────────────────────

    fun startGame(difficulty: Difficulty, operation: Operation) {
        this.difficulty = difficulty
        this.operation  = operation

        _score.value         = 0
        _streak.value        = 0
        _correctCount.value  = 0
        _wrongCount.value    = 0
        _isGameOver.value    = false
        _isNewBest.value     = false
        _combo.value         = 1
        _timeLeft.value      = difficulty.timeSeconds

        nextQuestion()
        startTimer()
    }

    fun restartGame() {
        startGame(difficulty, operation)
    }

    // ─────────────────────────────────────────
    // MARK: - Answer Logic
    // ─────────────────────────────────────────

    fun submitAnswer(answer: Int) {
        val question = _currentQuestion.value ?: return

        if (answer == question.answer) {
            // Correct
            _streak.value++
            _combo.value  = (_streak.value / 3 + 1).coerceAtMost(5)
            val points    = difficulty.pointsPerQuestion * _combo.value
            _score.value += points
            _correctCount.value++
        } else {
            // Wrong
            _streak.value = 0
            _combo.value  = 1
            _wrongCount.value++
        }

        nextQuestion()
    }

    private fun nextQuestion() {
        _currentQuestion.value = operation.generate(difficulty)
    }

    // ─────────────────────────────────────────
    // MARK: - Timer
    // ─────────────────────────────────────────

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value--
            }
            gameOver()
        }
    }

    private fun gameOver() {
        timerJob?.cancel()
        _isGameOver.value = true
        saveBestScore()
    }

    // ─────────────────────────────────────────
    // MARK: - Best Score
    // ─────────────────────────────────────────

    private fun saveBestScore() {
        if (_score.value > _bestScore.value) {
            _isNewBest.value  = true
            _bestScore.value  = _score.value
            viewModelScope.launch {
                dataStore.edit { prefs ->
                    prefs[BEST_SCORE_KEY] = _score.value
                }
            }
        }
    }
}