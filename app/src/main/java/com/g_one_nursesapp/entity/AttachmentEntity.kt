package com.g_one_nursesapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attachments")
data class AttachmentEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo
    val source: String,

    @ColumnInfo(name = "is_delete")
    val isDeleted: Boolean = false,

    @ColumnInfo(name = "message_id")
    val messageId: String,
)