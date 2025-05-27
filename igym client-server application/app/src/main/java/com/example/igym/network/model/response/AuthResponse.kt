package com.example.igym.network.model.response

data class AuthResponse(
    val userId: Long,
    val token: String,
    val email: String,
    val username: String,
    val fullName: String,
)