package com.team4.sajochamchi.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.team4.sajochamchi.data.model.SaveItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(saveItem: SaveItem)

    @Delete
    suspend fun delete(saveItem: SaveItem)

    @Query("SELECT * FROM SaveItem")
    fun getAll(): Flow<List<SaveItem>>

    @Query("SELECT * FROM SaveItem WHERE videoId = :videoId")
    fun getAllWithVideoId(videoId: String): Flow<List<SaveItem>>
}