package com.zhogin.reciepeapp.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.reciepeapp.features.profile.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel : ViewModel() {


    private var _refresh = MutableStateFlow(false)
    val refresh = _refresh.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val profileUiState: StateFlow<ProfileScreenUiState> = combine(refresh) {
        getUserInfo()
    }.flatMapLatest { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            ProfileScreenUiState()
        )

    private fun getUserInfo(): Flow<ProfileScreenUiState> = flow {
        emit(
            ProfileScreenUiState(
                isLoading = true
            )
        )

        // API Call
        delay(3000)
        emit(
            ProfileScreenUiState(
                isLoading = false,
                userInfo = user,
            )
        )

    }




    fun toRefresh() {
        _refresh.value = true
    }
}

private val user = User(
    id = 12345,
    name = "Ivan",
    email = "mr.i-zhogin@yandex.ru",
    myRecipeCount = 5,
    favoriteRecipeCount = 7,
    followers = 140,
)