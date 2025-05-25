package com.example.igym.manager

import android.content.Context

class AuthManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    fun getAuthToken(): String? {
        return sharedPref.getString("token", null)
    }

    fun getUserEmail(): String? {
        return sharedPref.getString("email", null)
    }

    fun getUsername(): String? {
        return sharedPref.getString("username", null)
    }

    fun saveAuthData(token: String, email: String, username: String) {
        sharedPref.edit().apply {
            putString("token", token)
            putString("email", email)
            putString("username", username)
            apply()
        }
    }

    fun logout() {
        sharedPref.edit().clear().apply()
    }
}