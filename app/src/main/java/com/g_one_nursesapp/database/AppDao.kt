package com.g_one_nursesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.g_one_nursesapp.entity.AttachmentEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOneAttachment(attachment: AttachmentEntity)

    @Query("SELECT * FROM attachments")
    fun fetchAttachments(): LiveData<List<AttachmentEntity>>
}