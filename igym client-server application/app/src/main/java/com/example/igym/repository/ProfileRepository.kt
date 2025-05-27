package com.example.igym.repository

import com.example.igym.network.api.ApiService

import com.example.igym.network.model.request.UserProfile

class ProfileRepository(private val api: ApiService) {

    suspend fun getProfile(userId: Long): UserProfile? {
        return try {
            api.getProfile(userId)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateProfile(profile: UserProfile, token: String): Boolean {
        return try {
            val response = api.updateProfile(profile, "Bearer $token")
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}


