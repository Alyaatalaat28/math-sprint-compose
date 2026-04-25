package com.AlyaaTalaat.mathsprint.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.AlyaaTalaat.mathsprint.ui.home.HomeScreen
import com.AlyaaTalaat.mathsprint.ui.game.GameScreen
import com.AlyaaTalaat.mathsprint.ui.result.ResultScreen
import com.AlyaaTalaat.mathsprint.viewmodel.GameViewModel

sealed class Screen(val route: String) {
    object Home   : Screen("home")
    object Game   : Screen("game")
    object Result : Screen("result")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: GameViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onStartGame = {
                    navController.navigate(Screen.Game.route)
                }
            )
        }
        composable(Screen.Game.route) {
            GameScreen(
                viewModel = viewModel,
                onGameOver = {
                    navController.navigate(Screen.Result.route)
                }
            )
        }
        composable(Screen.Result.route) {
            ResultScreen(
                viewModel = viewModel,
                onPlayAgain = {
                    navController.navigate(Screen.Game.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}