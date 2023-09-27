package com.team4.sajochamchi.data.model.searchvideo

import com.team4.sajochamchi.data.model.SaveItem

data class Item(
    val etag: String?,
    val id: Id?,
    val kind: String?,
    val snippet: Snippet?
)

fun Item.toSaveItem() = SaveItem(
    videoId = id?.videoId,
    channelId = snippet?.channelId,
    channelTitle = snippet?.channelTitle,
    title = snippet?.title,
    description = snippet?.description,
    thumbnailsUrl = snippet?.thumbnails?.high?.url,
    publishedAt = snippet?.publishedAt
)