package com.example.auth.presentation.di

import com.example.auth.presentation.login.LoginViewModel
import com.example.auth.domain.LoginUserDataValidator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val loginViewModelModule = module {
    singleOf(::LoginUserDataValidator)
    viewModelOf(::LoginViewModel)
}