package com.example.auth.domain;

class LoginUserDataValidator {

    fun isEmailValid(email: String): Boolean {
        return email.isNotBlank()
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }
}