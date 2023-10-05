package com.team4.sajochamchi.data.model.video

import com.team4.sajochamchi.data.model.SaveItem

data class Item(
    val etag: String?,
    val id: String?,
    val kind: String?,
    val snippet: Snippet?
)

fun Item.toSaveItem() = SaveItem(
    videoId = id,
    channelId = snippet?.channelId,
    channelTitle = snippet?.channelTitle,
    title = snippet?.localized?.title,
    description = snippet?.localized?.description,
    thumbnailsUrl = snippet?.thumbnails?.standard?.url,
    publishedAt = snippet?.publishedAt,
)
