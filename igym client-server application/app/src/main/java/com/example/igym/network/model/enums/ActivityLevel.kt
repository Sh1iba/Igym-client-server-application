package com.example.igym.network.model.enums

enum class ActivityLevel(val multiplier: Double, val displayName: String) {
    SEDENTARY(1.2, "Минимум/отсутствие физической нагрузки"),
    LIGHT(1.375, "1-3 раза в неделю"),
    MODERATE(1.55, "3 раза в неделю"),
    ACTIVE(1.725, "5 раз в неделю (интенсивно)"),
    VERY_ACTIVE(1.9, "Каждый день интенсивно или два раза в день"),
    EXTRA_ACTIVE(2.0, "Ежедневная физическая нагрузка + физическая работа")
}