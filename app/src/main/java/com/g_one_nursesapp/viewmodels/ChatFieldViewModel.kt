package com.g_one_nursesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.g_one_nursesapp.database.AppDatabase
import com.g_one_nursesapp.database.AppRepository
import com.g_one_nursesapp.entity.AttachmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFieldViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var appRepository: AppRepository

    init {
        val appDao = AppDatabase.useDatabase(application)?.appDao()
        appRepository = AppRepository(appDao!!)
    }

    fun insertOneAttachment(attachment: AttachmentEntity) = viewModelScope.launch(Dispatchers.IO) {
        appRepository.insertOneAttachment(attachment)
    }
}