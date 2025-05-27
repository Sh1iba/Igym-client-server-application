package com.example.igym.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.igym.network.model.request.UserProfile
import com.example.igym.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var userProfile by mutableStateOf<UserProfile?>(null)
        private set

    fun loadUserProfile(userId: Long) {
        viewModelScope.launch {
            val result = profileRepository.getProfile(userId)
            userProfile = result
        }
    }

    fun updateUserProfile(profile: UserProfile, token: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val success = profileRepository.updateProfile(profile, token)
            if (success) {
                userProfile = profile
                onSuccess()
            } else {
                onError("Ошибка при обновлении профиля")
            }
        }
    }
}
