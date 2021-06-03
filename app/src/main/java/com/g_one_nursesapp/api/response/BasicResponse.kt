package com.g_one_nursesapp.api.response

import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @field:SerializedName("error")
    val error: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = ""
)