package com.example.igym.manager

import android.util.Log
import com.example.igym.network.api.ApiClient
import com.example.igym.network.model.request.LoginRequest
import com.example.igym.network.model.response.AuthResponse

class LoginManager {
    private val apiService = ApiClient.gymApi

    suspend fun loginUser( email: String, password: String): Result<AuthResponse> {
        return try {
            Log.d("Login", "Пытаемся войти в аккаунт: $email")

            val request = LoginRequest(email, password)
            val response = apiService.loginUser(request)

            Log.d("Login", "Получен ответ: ${response.code()}")

            when {
                response.isSuccessful -> {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("Login", "Успешный вход. Токен: ${responseBody.token}")
                        Result.success(responseBody)
                    } else {
                        Log.e("Login", "Пустой ответ от сервера")
                        Result.failure(Exception("Empty response (${response.code()})"))
                    }
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "No error body"
                    Log.e("Login", "Ошибка ${response.code()}: $errorBody")
                    Result.failure(Exception("${response.code()}: $errorBody"))
                }
            }
        } catch (e: Exception) {
            Log.e("Login", "Ошибка сети: ${e.message}", e)
            Result.failure(e)
        }
    }
}