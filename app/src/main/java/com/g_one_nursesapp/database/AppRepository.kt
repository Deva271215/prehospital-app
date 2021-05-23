package com.g_one_nursesapp.database

import androidx.lifecycle.LiveData
import com.g_one_nursesapp.entity.AttachmentEntity

class AppRepository(private val appDao: AppDao) {
    val fetchAttachments: LiveData<List<AttachmentEntity>> = appDao.fetchAttachments()

    suspend fun insertOneAttachment(attachment: AttachmentEntity) {
        appDao.insertOneAttachment(attachment)
    }
}