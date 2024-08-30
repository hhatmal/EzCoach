package com.example.core.data.di

import com.example.core.data.network.RetrofitFactory
import com.example.core.domain.SessionStorage
import com.example.core.data.network.EncryptedSessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single { RetrofitFactory(get()).build() }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}