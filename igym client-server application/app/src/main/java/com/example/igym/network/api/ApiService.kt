package com.example.igym.network.api

import com.example.igym.network.model.request.LoginRequest
import com.example.igym.network.model.request.RegisterRequest
import com.example.igym.network.model.response.AuthResponse
import com.example.igym.network.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>


}