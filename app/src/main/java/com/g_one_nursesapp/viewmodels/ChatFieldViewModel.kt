package com.g_one_nursesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.g_one_nursesapp.database.AppDatabase
import com.g_one_nursesapp.database.AppRepository
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.entity.relation.MessageWithAttachments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFieldViewModel(application: Application): AndroidViewModel(application) {
    private var appRepository: AppRepository

    val fetchMessages: LiveData<List<MessageWithAttachments>>

    init {
        val appDao = AppDatabase.useDatabase(application)?.appDao()
        appRepository = AppRepository(appDao!!)

        fetchMessages = appDao.fetchMessages()
    }

    fun insertOneMessage(message: MessageEntity) = viewModelScope.launch(Dispatchers.IO) {
        appRepository.insertOneMessage(message)
    }

    fun insertOneAttachment(attachment: AttachmentEntity) = viewModelScope.launch(Dispatchers.IO) {
        appRepository.insertOneAttachment(attachment)
    }
}