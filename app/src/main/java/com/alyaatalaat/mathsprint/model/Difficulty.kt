package com.alyaatalaat.mathsprint.model

enum class Difficulty(
    val label: String,
    val emoji: String,
    val timeSeconds: Int,
    val maxNumber: Int,
    val pointsPerQuestion: Int
) {
    EASY(
        label = "Easy",
        emoji = "😊",
        timeSeconds = 60,
        maxNumber = 20,
        pointsPerQuestion = 10
    ),
    MEDIUM(
        label = "Medium",
        emoji = "🔥",
        timeSeconds = 45,
        maxNumber = 50,
        pointsPerQuestion = 20
    ),
    HARD(
        label = "Hard",
        emoji = "💀",
        timeSeconds = 30,
        maxNumber = 100,
        pointsPerQuestion = 30
    )
}