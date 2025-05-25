package com.example.igym.errorhandler

import android.util.Log
import com.example.igym.network.model.response.ErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ErrorParser {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(ErrorResponse::class.java)

    fun parseErrorMessage(json: String): String? {
        return try {
            val errorResponse = jsonAdapter.fromJson(json)
            errorResponse?.message
        } catch (e: Exception) {
            Log.e("ErrorParser", "Ошибка при парсинге ошибки: ${e.message}", e)
            null
        }
    }
}