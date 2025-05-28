package com.example.igym.network.model.response.workout



data class Exercise(
    val id: Long,
    val name: String,
    val description: String
)

data class WorkoutDetails(
    val id: Long,
    val title: String,
    val description: String,
    val muscleGroup: String,
    val exercises: List<Exercise>
)