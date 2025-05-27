package com.example.igym.network.model.request

data class UserProfile(
    val userId: Long,
    val sex: String,
    val birthDate: String,
    val height: Int,
    val weight: Double
)

enum class Sex {
    MALE, FEMALE
}