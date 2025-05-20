package com.example.igym.manager

import android.util.Log
import com.example.igym.network.api.ApiClient
import com.example.igym.network.model.request.LoginRequest
import com.example.igym.network.model.response.AuthResponse
import com.example.igym.network.model.response.ErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class LoginManager {
    private val apiService = ApiClient.gymApi

    suspend fun loginUser(email: String, password: String): Result<AuthResponse> {
        return try {
            Log.d("LoginManager", "Пытаемся войти в аккаунт: $email")

            val request = LoginRequest(email, password)
            val response = apiService.loginUser(request)

            Log.d("LoginManager", "Получен ответ: ${response.code()}")

            when {
                response.isSuccessful -> {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("LoginManager", "Успешный вход. Токен: ${responseBody.token}")
                        Result.success(responseBody)
                    } else {
                        Log.e("LoginManager", "Пустой ответ от сервера")
                        Result.failure(Exception("Empty response (${response.code()})"))
                    }
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "No error body"
                    Log.e("LoginManager", "Ошибка ${response.code()}: $errorBody")

                    // Парсим JSON и извлекаем message
                    val message = parseErrorMessage(errorBody)
                    Result.failure(Exception("${response.code()}: $message"))
                }
            }
        } catch (e: Exception) {
            Log.e("LoginManager", "Ошибка сети: ${e.message}", e)
            Result.failure(e)
        }
    }

    fun parseErrorMessage(json: String): String? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(ErrorResponse::class.java)

        return try {
            val errorResponse = jsonAdapter.fromJson(json)
            errorResponse?.message
        } catch (e: Exception) {
            Log.e("LoginManager", "Ошибка при парсинге ошибки: ${e.message}", e)
            null
        }
    }

}
