package com.g_one_nursesapp.entity

import android.os.Message
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "attachments", foreignKeys = [ForeignKey(
    entity = MessageEntity::class,
    parentColumns = ["id"],
    childColumns = ["message_id"],
    onDelete = ForeignKey.CASCADE
)])
data class AttachmentEntity(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @ColumnInfo
    @field:SerializedName("source")
    val source: String,

    @ColumnInfo(name = "message_id")
    @field:SerializedName("message_id")
    val messageId: String,
)