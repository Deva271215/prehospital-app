package com.g_one_nursesapp.database

import androidx.lifecycle.LiveData
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity

class AppRepository(private val appDao: AppDao) {
    // Messages
    val fetchMessage: LiveData<List<MessageEntity>> = appDao.fetchMessages()
    suspend fun insertOneMessage(message: MessageEntity) {
        appDao.insertOneMessage(message)
    }

    // Attachment
    val fetchAttachments: LiveData<List<AttachmentEntity>> = appDao.fetchAttachments()
    suspend fun insertOneAttachment(attachment: AttachmentEntity) {
        appDao.insertOneAttachment(attachment)
    }
}