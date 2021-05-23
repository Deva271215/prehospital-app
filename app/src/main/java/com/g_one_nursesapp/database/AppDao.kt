package com.g_one_nursesapp.database

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.room.*
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.entity.relation.MessageWithAttachments

@Dao
interface AppDao {
    // Message
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneMessage(message: MessageEntity)

    @Transaction
    @Query("SELECT * FROM messages")
    fun fetchMessages(): LiveData<List<MessageWithAttachments>>

    // Attachment
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneAttachment(attachment: AttachmentEntity)

    @Query("SELECT * FROM attachments")
    fun fetchAttachments(): LiveData<List<AttachmentEntity>>
}