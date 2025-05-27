package com.example.igym.manager

import android.content.Context

class AuthManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    fun getAuthToken(): String? = sharedPref.getString("token", null)

    fun getUserEmail(): String? = sharedPref.getString("email", null)

    fun getUsername(): String? = sharedPref.getString("username", null)

    fun getFullName(): String? = sharedPref.getString("fullName", null)

    fun getSex(): String? = sharedPref.getString("sex", null)

    fun getBirthDate(): String? = sharedPref.getString("birthDate", null)

    fun getAge(): Int = sharedPref.getInt("age", -1)

    fun getUserId(): Long = sharedPref.getLong("userId", -1L)

    fun getHeight(): Int = sharedPref.getInt("height", -1)

    fun getWeight(): Float = sharedPref.getFloat("weight", -1f)

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
