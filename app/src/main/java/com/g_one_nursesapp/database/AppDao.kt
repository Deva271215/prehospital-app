package com.g_one_nursesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.g_one_nursesapp.entity.MessageEntity

@Dao
interface AppDao {
    // Message
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneMessage(message: MessageEntity)

    @Query("DELETE FROM messages")
    suspend fun deleteMessages()

    @Transaction
    @Query("SELECT * FROM messages")
    fun fetchMessages(): LiveData<List<MessageEntity>>
}