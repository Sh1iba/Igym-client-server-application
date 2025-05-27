package com.example.igym.network.model.response

data class ProfileResponse (
        val userId: Long,
        val sex: String,
        val birthDate: String,
        val height: Int,
        val weight: Double,
        val age: Int

)

