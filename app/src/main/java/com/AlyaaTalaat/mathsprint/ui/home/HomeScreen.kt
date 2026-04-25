package com.AlyaaTalaat.mathsprint.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.AlyaaTalaat.mathsprint.model.Difficulty
import com.AlyaaTalaat.mathsprint.model.Operation
import com.AlyaaTalaat.mathsprint.viewmodel.GameViewModel

@Composable
fun HomeScreen(
    viewModel: GameViewModel,
    onStartGame: () -> Unit
) {
    val bestScore by viewModel.bestScore.collectAsState()

    var selectedDifficulty by remember { mutableStateOf(Difficulty.EASY) }
    var selectedOperation  by remember { mutableStateOf(Operation.ADDITION) }

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
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            // ── Title ──
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🧮", fontSize = 64.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Math Sprint",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "How fast can you solve?",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }

            // ── Best Score ──
            if (bestScore > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White.copy(alpha = 0.1f))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text("🏆", fontSize = 16.sp)
                    Text(
                        text = "Best: $bestScore",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // ── Difficulty Selector ──
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "DIFFICULTY",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Difficulty.entries.forEach { difficulty ->
                        val isSelected = selectedDifficulty == difficulty
                        val bgColor by animateColorAsState(
                            if (isSelected) Color(0xFF6C63FF) else Color.White.copy(alpha = 0.1f),
                            label = "difficulty_color"
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(bgColor)
                                .clickable { selectedDifficulty = difficulty }
                                .padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(difficulty.emoji, fontSize = 24.sp)
                            Text(
                                difficulty.label,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "${difficulty.timeSeconds}s",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // ── Operation Selector ──
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "OPERATION",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                // Row 1 — four operations
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Operation.entries.take(4).forEach { operation ->
                        val isSelected = selectedOperation == operation
                        val bgColor by animateColorAsState(
                            if (isSelected) Color(0xFF6C63FF) else Color.White.copy(alpha = 0.1f),
                            label = "op_color"
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgColor)
                                .clickable { selectedOperation = operation }
                                .padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                operation.symbol,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                operation.label.take(3),
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
                // Row 2 — Mix button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (selectedOperation == Operation.MIX)
                                Color(0xFF6C63FF)
                            else Color.White.copy(alpha = 0.1f)
                        )
                        .clickable { selectedOperation = Operation.MIX }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${Operation.MIX.symbol} ${Operation.MIX.label}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // ── Start Button ──
            Button(
                onClick = {
                    viewModel.startGame(selectedDifficulty, selectedOperation)
                    onStartGame()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6C63FF)
                )
            ) {
                Text(
                    "Start Game 🚀",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}