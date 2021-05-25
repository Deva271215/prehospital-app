package com.g_one_nursesapp.api.socket

import com.g_one_nursesapp.api.response.ChatResponse
import com.google.gson.annotations.SerializedName

data class SendBulkMessagesPayload(
    @field:SerializedName("chat")
    var chat: ChatResponse? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("creation_time")
    val creationTime: String,

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("response")
    val response: String? = null,

    @field:SerializedName("condition")
    val condition: String? = null,

    @field:SerializedName("action")
    val action: String? = null,
)