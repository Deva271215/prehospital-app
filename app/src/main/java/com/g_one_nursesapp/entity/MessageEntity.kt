package com.g_one_nursesapp.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "messages")
class MessageEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo
    val message: String,

    @ColumnInfo
    @Nullable
    val result: String?,

    @ColumnInfo
    @Nullable
    val response: String?,

    @ColumnInfo
    @Nullable
    val condition: String?,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false,
)