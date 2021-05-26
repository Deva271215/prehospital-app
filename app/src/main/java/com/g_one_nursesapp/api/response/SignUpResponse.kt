package com.g_one_nursesapp.api.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse (
        @field:SerializedName("error")
        val error: Boolean?,

        @field:SerializedName("statusCode")
        val statusCode: Int?,

        @field:SerializedName("message")
        val message: String?,

        @field:SerializedName("data")
        val data: SignUpData?
)

data class SignUpData(
        val user: UserResponse?,
)