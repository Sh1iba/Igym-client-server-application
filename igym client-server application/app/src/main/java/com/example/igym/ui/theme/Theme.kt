package com.example.igym.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Темная цветовая схема приложения.
 *
 * Использует оттенки фиолетового и розового для темной темы:
 * - primary: Purple80
 * - secondary: PurpleGrey80
 * - tertiary: Pink80
 */
private val DarkColorScheme = darkColorScheme(
    primary = colorDarkGray,
    secondary = colorYellowGreen,
    tertiary = colorDarkPurple
)

/**
 * Светлая цветовая схема приложения.
 *
 * Использует более насыщенные оттенки для светлой темы:
 * - primary: Purple40
 * - secondary: PurpleGrey40
 * - tertiary: Pink40
 */
private val LightColorScheme = lightColorScheme(
    primary = colorLightGray,
    secondary = colorLightWhite,
    tertiary = colorLightPurple

    /* Дополнительные цвета могут быть переопределены:
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * Основная тема приложения IGym, реализующая Material Design 3.
 *
 * Автоматически адаптируется к системным настройкам темы и поддерживает:
 * - Светлую/темную тему
 * - Динамические цвета (на Android 12+)
 * - Кастомные цветовые схемы
 *
 * @param darkTheme Определяет, должна ли использоваться темная тема.
 *                  По умолчанию соответствует системной настройке.
 * @param dynamicColor Включает динамические цвета (если доступно).
 *                    По умолчанию true (Android 12+).
 * @param content Composable-контент, к которому применяется тема.
 *
 * @sample com.example.igym.ui.theme.ThemePreview
 *
 * ## Пример использования:
 * ```
 * IgymTheme {
 *     // Ваш Composable-контент
 *     AppScreen()
 * }
 * ```
 *
 * ## Логика выбора цветовой схемы:
 * 1. Для Android 12+ с включенными динамическими цветами:
 *    - Использует dynamicDarkColorScheme/dynamicLightColorScheme
 * 2. Для других случаев:
 *    - Использует DarkColorScheme/LightColorScheme
 */
@Composable
fun IgymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}