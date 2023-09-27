package com.team4.sajochamchi.data.model.video

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SaveItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val videoId: String,//https://www.youtube.com/watch?v=id
    val channelTitle: String,//체널 이름
    val title: String,//localized.title
    val description: String,//설명
    val thumbnailsUrl: String,//StandardUrl
    val publishedAt: String,
)