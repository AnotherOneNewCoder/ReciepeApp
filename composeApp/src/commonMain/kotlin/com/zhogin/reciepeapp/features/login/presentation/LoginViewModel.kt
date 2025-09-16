package com.zhogin.reciepeapp.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, passwd: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Idle
            try {
                delay(3000)
                if (email == "mr.i-zhogin@yandex.ru" && passwd == "test") {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error(
                        "Invalid email or password!"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message.toString())
            }
        }
    }
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}