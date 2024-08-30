package com.example.auth.presentation.login

sealed interface LoginAction {
    data object onTogglePasswordVisibilityClick: LoginAction
    data object onLoginClick: LoginAction
}