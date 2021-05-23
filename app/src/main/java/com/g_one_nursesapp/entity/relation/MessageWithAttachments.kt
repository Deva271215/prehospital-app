package com.g_one_nursesapp.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity

data class MessageWithAttachments(
    @Embedded
    val message: MessageEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "message_id"
    )
    val attachments: List<AttachmentEntity>
)