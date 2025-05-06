package com.example.igym.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Объект, содержащий типографические стили приложения.
 *
 * Определяет набор текстовых стилей в соответствии с Material Design 3,
 * которые могут быть использованы во всем приложении для обеспечения
 * единообразия внешнего вида текста.
 *
 * ## Пример использования:
 * ```
 * Text(
 *     text = "Пример текста",
 *     style = Typography.bodyLarge
 * )
 * ```
 *
 * @property bodyLarge Основной стиль для длинного текста (параграфы)
 * @property titleLarge Стиль для крупных заголовков (по умолчанию закомментирован)
 * @property labelSmall Стиль для мелких подписей (по умолчанию закомментирован)
 */
val Typography = Typography(
    /**
     * Стиль для основного текста (body text).
     *
     * Характеристики:
     * - Шрифт: Системный по умолчанию
     * - Насыщенность: Normal (400)
     * - Размер: 16.sp
     * - Межстрочный интервал: 24.sp
     * - Межбуквенный интервал: 0.5.sp
     */
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /*
    /**
     * Стиль для крупных заголовков.
     *
     * Характеристики:
     * - Шрифт: Системный по умолчанию
     * - Насыщенность: Normal (400)
     * - Размер: 22.sp
     * - Межстрочный интервал: 28.sp
     * - Межбуквенный интервал: 0.sp
     */
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    /**
     * Стиль для мелких подписей и меток.
     *
     * Характеристики:
     * - Шрифт: Системный по умолчанию
     * - Насыщенность: Medium (500)
     * - Размер: 11.sp
     * - Межстрочный интервал: 16.sp
     * - Межбуквенный интервал: 0.5.sp
     */
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)