# 🧮 Math Sprint

A fast-paced math game built with Kotlin and Jetpack Compose.
Built as a learning project to explore Android native development.

## Features

- 3 difficulty levels (Easy, Medium, Hard)
- 5 operation modes (+, −, ×, ÷, Mix)
- Combo multiplier system
- Streak counter
- Accuracy tracking
- Best score persistence with DataStore
- Animated question transitions
- Timer bar with color feedback

## Screens

- **Home** — difficulty and operation selector
- **Game** — questions with timer and combo system
- **Result** — score summary with accuracy

<img width="1080" height="2400" alt="Screenshot_20260425_182458" src="https://github.com/user-attachments/assets/124457e9-be2e-429c-9627-aa9df82428a9" />
<img width="1080" height="2400" alt="Screenshot_20260425_182539" src="https://github.com/user-attachments/assets/793b377e-5079-4e6e-8a43-b7a4f4fcd87d" />
<img width="1080" height="2400" alt="Screenshot_20260425_182559" src="https://github.com/user-attachments/assets/2b53f314-45a2-4697-9de8-714201c19edd" />
<img width="1080" height="2400" alt="Screenshot_20260425_182613" src="https://github.com/user-attachments/assets/c7bf8fa8-b10a-4c25-b636-12bf5c2826eb" />

## Concepts Learned

- `ViewModel` + `StateFlow` (like Cubit + emit)
- `collectAsState()` (like BlocBuilder)
- `LaunchedEffect` (like initState / BlocListener)
- `AnimatedContent` for question transitions
- `DataStore` for best score persistence
- `NavHost` + `NavController` for navigation
- `Coroutines` + `delay` for timer logic
- `Brush.verticalGradient` for backgrounds
- `animateColorAsState` for smooth UI transitions
- `sealed class` for navigation routes
- Kotlin `enum class` with properties and methods
- Kotlin `data class` (like Freezed models)

## Difficulty Levels

| Level | Time | Max Number | Points/Question |
|---|---|---|---|
| 😊 Easy | 60s | 20 | 10 |
| 🔥 Medium | 45s | 50 | 20 |
| 💀 Hard | 30s | 100 | 30 |

## Tech Stack

- Kotlin
- Jetpack Compose
- Material3
- ViewModel + StateFlow
- DataStore
- Navigation Compose
- Coroutines
