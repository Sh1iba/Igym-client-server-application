package com.example.igym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.igym.ui.theme.IgymTheme

/**
 * Главная активность приложения, содержащая Compose-интерфейс.
 *
 * Основные функции:
 * - Устанавливает edge-to-edge отображение.
 * - Использует тему [IgymTheme].
 * - Отображает приветствие через Composable-функцию [Greeting].
 *
 * @see ComponentActivity
 */
class MainActivity : ComponentActivity() {
    /**
     * Создает UI приложения с помощью Jetpack Compose.
     *
     * @param savedInstanceState Сохраненное состояние активности (может быть `null`).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IgymTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Отображает приветственное сообщение.
 *
 * @param name Имя для приветствия.
 * @param modifier [Modifier] для настройки внешнего вида текста.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * Превью функции [Greeting] в Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IgymTheme {
        Greeting("Android")
    }
}