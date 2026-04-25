package com.alyaatalaat.mathsprint.model

enum class Operation(val label: String, val symbol: String) {
    ADDITION("Addition", "+"),
    SUBTRACTION("Subtraction", "−"),
    MULTIPLICATION("Multiplication", "×"),
    DIVISION("Division", "÷"),
    MIX("Mix", "🎲");

    fun generate(difficulty: Difficulty): Question {
        val op = if (this == MIX) entries.filter { it != MIX }.random() else this
        return when (op) {
            ADDITION -> {
                val a = (1..difficulty.maxNumber).random()
                val b = (1..difficulty.maxNumber).random()
                Question("$a + $b", a + b)
            }
            SUBTRACTION -> {
                val a = (1..difficulty.maxNumber).random()
                val b = (1..a).random()
                Question("$a − $b", a - b)
            }
            MULTIPLICATION -> {
                val a = (1..difficulty.maxNumber / 2).random()
                val b = (1..10).random()
                Question("$a × $b", a * b)
            }
            DIVISION -> {
                val b = (1..10).random()
                val answer = (1..difficulty.maxNumber / 2).random()
                val a = answer * b
                Question("$a ÷ $b", answer)
            }
            MIX -> throw IllegalStateException()
        }
    }
}