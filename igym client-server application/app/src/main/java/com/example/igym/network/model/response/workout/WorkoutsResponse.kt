package com.example.igym.network.model.response.workout



data class WorkoutsResponse (
    val id: Long,
    val title: String,
    val description: String,
    val muscleGroup: String
)
