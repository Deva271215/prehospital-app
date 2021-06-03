package com.g_one_nursesapp.api.requests

import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @field:SerializedName("topic")
    val topic: String,

    @field:SerializedName("data")
    val data: DataSchema,
)

data class DataSchema(
    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("body")
    val body: String,

    @field:SerializedName("click_action")
    val clickAction: String,

    @field:SerializedName("chat_id")
    val chatId: String,
)