package com.example.core.data.network

import android.content.SharedPreferences
import com.example.core.data.mapper.toAuthInfo
import com.example.core.data.mapper.toAuthInfoSerializable
import com.example.core.domain.AuthInfo
import com.example.core.domain.SessionStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences
): SessionStorage {
    override fun get(): AuthInfo? {
        val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
        return json?.let {
            Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
        }
    }

    override fun set(info: AuthInfo?) {
        if(info == null) {
            sharedPreferences.edit().remove(KEY_AUTH_INFO).commit()
            return
        }

        val json = Json.encodeToString(info.toAuthInfoSerializable())
        sharedPreferences
            .edit()
            .putString(KEY_AUTH_INFO, json)
            .commit()
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}