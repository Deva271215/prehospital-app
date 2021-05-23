package com.g_one_nursesapp.database

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity

@Dao
interface AppDao {
    // Message
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneMessage(message: MessageEntity)

    @Query("SELECT * FROM messages")
    fun fetchMessages(): LiveData<List<MessageEntity>>

    // Attachment
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneAttachment(attachment: AttachmentEntity)

    @Query("SELECT * FROM attachments")
    fun fetchAttachments(): LiveData<List<AttachmentEntity>>
}