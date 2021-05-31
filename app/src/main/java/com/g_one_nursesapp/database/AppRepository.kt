package com.g_one_nursesapp.database

import androidx.lifecycle.LiveData
import com.g_one_nursesapp.entity.MessageEntity

class AppRepository(private val appDao: AppDao) {
    // Messages
    val fetchMessages: LiveData<List<MessageEntity>> = appDao.fetchMessages()
    suspend fun insertOneMessage(message: MessageEntity) {
        appDao.insertOneMessage(message)
    }
    suspend fun deleteMessages() {
        appDao.deleteMessages()
    }
}