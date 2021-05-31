package com.g_one_nursesapp.preference

import android.content.Context
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.api.response.LoginData
import com.g_one_nursesapp.api.response.UserResponse
import com.google.gson.Gson

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFERENCE_NAME = "user_preference"
        private const val USER = "user"
        private const val ACCESS_TOKEN = "access_token"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val IS_HOSPITAL_SELECTED = "is_hospital_selected"
        private const val SELECTED_HOSPITAL = "selected_hospital"
        private const val ACTIVE_CHAT = "active_chat"
    }

    private val gson = Gson()
    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    // User login
    fun setLoginData(value: LoginData) {
        val e = preferences.edit()
        e.apply {
            putString(USER, gson.toJson(value.user))
            putString(ACCESS_TOKEN, value.access_token)
            putBoolean(IS_LOGGED_IN, true)
        }.apply()
    }
    fun getLoginData(): LoginData {
        val json = gson.fromJson(preferences.getString(USER, ""), UserResponse::class.java)
        val access_token = preferences.getString(ACCESS_TOKEN, "")
        val m = LoginData(user = json, access_token = access_token)

        return m
    }

    // Hospital
    fun setSelectedHospital(value: HospitalsResponse) {
        val e = preferences.edit()
        val gson = Gson()
        e.apply { putString(SELECTED_HOSPITAL, gson.toJson(value)) }.apply()
    }
    fun setIsHospitalSelected(value: Boolean) {
        val e = preferences.edit()
        e.apply{ putBoolean(IS_HOSPITAL_SELECTED, value)}.apply()
    }
    fun getSelectedHospital(): HospitalsResponse {
        return gson.fromJson(preferences.getString(SELECTED_HOSPITAL, ""), HospitalsResponse::class.java)
    }
    fun getIsHospitalSelected(): Boolean = preferences.getBoolean(IS_HOSPITAL_SELECTED, false)

    // Chat
    fun setActiveChat(value: ChatResponse) {
        val e = preferences.edit()
        e.apply{ putString(ACTIVE_CHAT, Gson().toJson(value)) }.apply()
    }
    fun getActiveChat(): String? = preferences.getString(ACTIVE_CHAT, "")
}