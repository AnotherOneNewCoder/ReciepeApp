package com.zhogin.reciepeapp.features.profile.presentation

import com.zhogin.reciepeapp.features.profile.domain.User

data class ProfileScreenUiState(
    val userInfo: User? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)