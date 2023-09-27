package com.team4.sajochamchi.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.team4.sajochamchi.data.model.video.SaveItem

@Dao
interface ItemDao {
    @Insert
    fun insert(saveItem: SaveItem)

    @Update
    fun update(saveItem: SaveItem)

    @Delete
    fun delete(saveItem: SaveItem)

    @Query("SELECT * FROM SaveItem")
    fun getAll(): List<SaveItem>

    @Query("SELECT * FROM SaveItem WHERE id =:id")
    fun getOne(id: Int): SaveItem

    @Query("SELECT * FROM SaveItem WHERE videoId = :videoId")
    fun getAllWithVideoId(videoId: String): List<SaveItem>
}