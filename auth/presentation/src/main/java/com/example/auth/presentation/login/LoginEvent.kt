package com.example.auth.presentation.login

import com.example.core.presentation.ui.UiText

sealed interface LoginEvent {
    data object Success: LoginEvent
    data class Error(val error: UiText): LoginEvent
}