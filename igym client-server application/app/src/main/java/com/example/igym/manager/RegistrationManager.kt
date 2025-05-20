package com.example.igym.manager

import android.util.Log
import com.example.igym.network.api.ApiClient
import com.example.igym.network.model.request.LoginRequest
import com.example.igym.network.model.request.RegisterRequest
import com.example.igym.network.model.response.AuthResponse
import com.example.igym.network.model.response.ErrorResponse
import com.example.igym.network.model.response.RegisterResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class RegistrationManager {
    private val apiService = ApiClient.gymApi

    suspend fun registerUser(username: String, email: String, password: String, fullName: String): Result<RegisterResponse> {
        return try {
            Log.d("RegistrationManager", "Пытаемся создать аккаунт: $email")

            val request = RegisterRequest(username,email, password, fullName)
            val response = apiService.registerUser(request)

            Log.d("RegistrationManager", "Получен ответ: ${response.code()}")

            when {
                response.isSuccessful -> {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("RegistrationManager", "Аккаунт успешно создан: ${responseBody}")
                        Result.success(responseBody)
                    } else {
                        Log.e("RegistrationManager", "Пустой ответ от сервера")
                        Result.failure(Exception("Empty response (${response.code()})"))
                    }
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: "No error body"
                    Log.e("RegistrationManager", "Ошибка ${response.code()}: $errorBody")


                    val message = parseErrorMessage(errorBody)
                    Result.failure(Exception(message ?: "Неизвестная ошибка"))
                }
            }
        } catch (e: Exception) {
            Log.e("RegistrationManager", "Ошибка сети: ${e.message}", e)
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
            Log.e("RegistrationManager", "Ошибка при парсинге ошибки: ${e.message}", e)
            null
        }
    }

}
