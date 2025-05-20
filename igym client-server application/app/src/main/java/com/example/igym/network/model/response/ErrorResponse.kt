package com.example.igym.network.model.response

data class ErrorResponse(
    val success: Boolean,
    val message: String,
    val errorCode: String
)