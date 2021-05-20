package com.g_one_nursesapp.preference

import android.content.Context
import com.g_one_nursesapp.api.response.LoginData
import com.g_one_nursesapp.api.response.UserResponse
import com.google.gson.Gson

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFERENCE_NAME = "user_preference"
        private const val USER = "user"
        private const val ACCESS_TOKEN = "access_token"
        private const val IS_LOGGED_IN = "is_logged_in"
    }

    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setLoginData(value: LoginData) {
        val e = preferences.edit()
        val gson = Gson()
        e.apply {
            putString(USER, gson.toJson(value.user))
            putString(ACCESS_TOKEN, value.access_token)
            putBoolean(IS_LOGGED_IN, true)
        }.apply()
    }

    fun getIsLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun getLoginData(): LoginData {
        val gson = Gson()
        val json = gson.fromJson(preferences.getString(USER, ""), UserResponse::class.java)
        val access_token = preferences.getString(ACCESS_TOKEN, "")
        val m = LoginData(user = json, access_token = access_token)

        return m
    }
}