package com.g_one_nursesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.g_one_nursesapp.database.AppDatabase
import com.g_one_nursesapp.database.AppRepository
import com.g_one_nursesapp.entity.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFieldViewModel(application: Application): AndroidViewModel(application) {
    private var appRepository: AppRepository

    val fetchMessages: LiveData<List<MessageEntity>>

    init {
        val appDao = AppDatabase.useDatabase(application)?.appDao()
        appRepository = AppRepository(appDao!!)

        fetchMessages = appRepository.fetchMessages
    }

    fun insertOneMessage(message: MessageEntity) = viewModelScope.launch(Dispatchers.IO) {
        appRepository.insertOneMessage(message)
    }

    fun deleteMessages() = viewModelScope.launch(Dispatchers.IO) {
        appRepository.deleteMessages()
    }
}