package com.g_one_nursesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.g_one_nursesapp.database.AppDatabase
import com.g_one_nursesapp.database.AppRepository
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var appRepository: AppRepository

    init {
        val appDao = AppDatabase.useDatabase(application)?.appDao()
        appRepository = AppRepository(appDao!!)
    }

    fun insertOneMessage(message: MessageEntity) = viewModelScope.launch(Dispatchers.IO) {
        appRepository.insertOneMessage(message)
    }
    fun deleteMessages() = viewModelScope.launch(Dispatchers.IO) {
        appRepository.deleteMessages()
    }

    fun insertOneAttachment(attachment: AttachmentEntity) = viewModelScope.launch(Dispatchers.IO) {
        appRepository.insertOneAttachment(attachment)
    }
}