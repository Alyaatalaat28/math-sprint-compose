package com.alyaatalaat.mathsprint.ui.game

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alyaatalaat.mathsprint.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onGameOver: () -> Unit
) {
    val score           by viewModel.score.collectAsState()
    val streak          by viewModel.streak.collectAsState()
    val timeLeft        by viewModel.timeLeft.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val isGameOver      by viewModel.isGameOver.collectAsState()
    val combo           by viewModel.combo.collectAsState()

    // Navigate to result when game is over
    LaunchedEffect(isGameOver) {
        if (isGameOver) onGameOver()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // ── Stats Row ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Score
                Column(horizontalAlignment = Alignment.Start) {
                    Text("SCORE", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp, letterSpacing = 1.sp)
                    Text("$score", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }

                // Combo
                if (combo > 1) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFFFFD700).copy(alpha = 0.2f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "🔥 x$combo Combo!",
                            color = Color(0xFFFFD700),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                // Streak
                Column(horizontalAlignment = Alignment.End) {
                    Text("STREAK", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp, letterSpacing = 1.sp)
                    Text("$streak", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
            }

            // ── Timer Bar ──
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("⏱ Time Left", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text("${timeLeft}s", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                LinearProgressIndicator(
                    progress = { timeLeft / 60f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50)),
                    color = when {
                        timeLeft > 20 -> Color(0xFF6C63FF)
                        timeLeft > 10 -> Color(0xFFFFB347)
                        else          -> Color(0xFFFF6B6B)
                    },
                    trackColor = Color.White.copy(alpha = 0.1f)
                )
            }

            // ── Question Card ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = currentQuestion?.expression ?: "",
                    transitionSpec = {
                        slideInVertically { -it } + fadeIn() togetherWith
                                slideOutVertically { it } + fadeOut()
                    },
                    label = "question_animation"
                ) { expression ->
                    Text(
                        text = expression,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // ── Answer Options ──
            currentQuestion?.let { question ->
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    question.options.chunked(2).forEach { rowOptions ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            rowOptions.forEach { option ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White.copy(alpha = 0.1f))
                                        .clickable { viewModel.submitAnswer(option) }
                                        .padding(vertical = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$option",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}