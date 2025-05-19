package com.example.igym.network.model.response

import java.time.LocalDate

data class UserResponse(
    val userID: Long,
    val username: String,
    val email: String,
    val fullName: String,
    val bio: String?,
    val dateOfBirth: LocalDate?
)