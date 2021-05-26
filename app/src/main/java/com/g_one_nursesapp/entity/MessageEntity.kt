package com.g_one_nursesapp.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "messages")
class MessageEntity(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @ColumnInfo
    @field:SerializedName("message")
    val message: String,

    @ColumnInfo
    @Nullable
    @field:SerializedName("result")
    val result: String? = null,

    @ColumnInfo
    @Nullable
    @field:SerializedName("response")
    val response: String? = null,

    @ColumnInfo
    @Nullable
    @field:SerializedName("condition")
    val condition: String? = null,

    @ColumnInfo
    @Nullable
    @field:SerializedName("action")
    val action: String? = null,

    @ColumnInfo(name = "creation_time")
    @field:SerializedName("creation_time")
    val creationTime: String? = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
)