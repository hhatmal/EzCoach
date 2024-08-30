package com.example.core.domain

interface SessionStorage {
    fun get(): AuthInfo?
    fun set(info: AuthInfo?)
}