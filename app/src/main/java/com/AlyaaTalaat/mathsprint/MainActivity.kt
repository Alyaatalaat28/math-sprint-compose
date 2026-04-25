package com.AlyaaTalaat.mathsprint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.AlyaaTalaat.mathsprint.navigation.AppNavigation
import com.AlyaaTalaat.mathsprint.ui.theme.MathSprintTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathSprintTheme {
                AppNavigation()
            }
        }
    }
}