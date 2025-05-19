package com.example.igym.network.model.response

data class AuthResponse(
    val token: String,
    val email: String,
    val username: String
)