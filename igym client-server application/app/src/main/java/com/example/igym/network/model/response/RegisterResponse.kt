package com.example.igym.network.model.response

import java.time.LocalDate

data class RegisterResponse(
    val userID: Long,
    val username: String,
    val email: String,
    val fullName: String,

)