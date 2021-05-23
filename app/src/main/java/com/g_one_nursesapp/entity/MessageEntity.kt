package com.g_one_nursesapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
class MessageEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo
    val message: String,

    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false
)