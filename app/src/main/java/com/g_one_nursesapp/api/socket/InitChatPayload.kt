package com.g_one_nursesapp.api.socket

import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.api.response.UserResponse
import com.google.gson.annotations.SerializedName

data class InitChatPayload(
    @field:SerializedName("user")
    val user: UserResponse,

    @field:SerializedName("hospital")
    val hospitals: HospitalsResponse
)