package com.example.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.LoginUserDataValidator

import com.example.core.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val loginUserDataValidator: LoginUserDataValidator
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set
    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        snapshotFlow { state.email }
            .onEach { email ->
                // composition bug with not being redrawn
                val isEmailValid = loginUserDataValidator.isEmailValid(email.toString())
                state = state.copy(
                    isEmailValid = isEmailValid,
                    canLogin = isEmailValid && state.isPasswordValid
                )
            }
            .launchIn(viewModelScope)

        snapshotFlow { state.password }
            .onEach { password ->
                // composition bug with not being redrawn with default values in data class
                val isPasswordValid = loginUserDataValidator.validatePassword(password.toString())
                state = state.copy(
                    isPasswordValid = isPasswordValid,
                    canLogin = state.isEmailValid && isPasswordValid
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.onLoginClick -> login()
            LoginAction.onTogglePasswordVisibilityClick -> togglePasswordVisibility()
        }
    }

    private fun togglePasswordVisibility() {
        state = state.copy(isPasswordVisible = !state.isPasswordVisible)
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                state.email.text.toString().trim(),
                state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)

            when (result) {
                is Result.Error -> {
                    /*                if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(LoginEvent.Error(UiText.StringResource(R.string.error_email_exists)))
                    } else {
                        eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }*/
                }

                is Result.Success -> {
                    eventChannel.send(LoginEvent.Success)
                }
            }
        }
    }
}