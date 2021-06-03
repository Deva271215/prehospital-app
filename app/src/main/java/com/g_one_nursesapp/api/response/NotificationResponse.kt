package com.g_one_nursesapp.api.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @field:SerializedName("message_id")
    val messageId: Int,
)