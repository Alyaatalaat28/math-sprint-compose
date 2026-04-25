package com.AlyaaTalaat.mathsprint.model

data class Question(
    val expression: String,
    val answer: Int
) {
    // Generate 3 wrong options + 1 correct = 4 choices total
    val options: List<Int> by lazy {
        val wrong = mutableSetOf<Int>()
        while (wrong.size < 3) {
            val offset = (-10..10).random()
            val candidate = answer + offset
            if (candidate != answer && candidate >= 0) {
                wrong.add(candidate)
            }
        }
        (wrong.toList() + answer).shuffled()
    }
}