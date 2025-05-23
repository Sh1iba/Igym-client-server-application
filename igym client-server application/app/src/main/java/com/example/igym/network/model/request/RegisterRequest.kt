package com.example.igym.network.model.request

import java.time.LocalDate

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val fullName: String,

)