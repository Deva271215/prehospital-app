package com.g_one_nursesapp.entity

import android.os.Message
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "attachments", foreignKeys = [ForeignKey(
    entity = MessageEntity::class,
    parentColumns = ["id"],
    childColumns = ["message_id"],
    onDelete = ForeignKey.CASCADE
)])
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