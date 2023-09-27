package com.team4.sajochamchi.data.model.video

data class Item(
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
)

fun Item.toSaveItem() = SaveItem(
    videoId = id,
    channelTitle = snippet.channelTitle,
    title = snippet.localized.title,
    description = snippet.localized.description,
    thumbnailsUrl = snippet.thumbnails.standard.url,
    publishedAt = snippet.publishedAt
)