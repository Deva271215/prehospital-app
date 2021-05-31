package com.g_one_nursesapp.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.g_one_nursesapp.api.response.ChatResponse
import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "messages")
class MessageEntity(
    @PrimaryKey
    @field:SerializedName("id")
    var id: String,

    @ColumnInfo
    @field:SerializedName("message")
    var message: String,

    @ColumnInfo
    @Nullable
    @field:SerializedName("result")
    var result: String? = null,

    @ColumnInfo
    @Nullable
    @field:SerializedName("response")
    var response: String? = null,

    @ColumnInfo
    @Nullable
    @field:SerializedName("condition")
    var condition: String? = null,

    @ColumnInfo
    @Nullable
    @field:SerializedName("action")
    var action: String? = null,

    @ColumnInfo
    @Nullable
    var attachments: String? = "",

    @ColumnInfo(name = "creation_time")
    @field:SerializedName("creation_time")
    var creationTime: String? = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),

    @Ignore
    @field:SerializedName("chat")
    var chat: ChatResponse? = null
){
    constructor(): this(id = "", message = "", result = "", response = "", condition = "", action = "", attachments = "", creationTime = "", chat = null)
}