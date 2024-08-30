package com.example.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val canLogin: Boolean = false
)