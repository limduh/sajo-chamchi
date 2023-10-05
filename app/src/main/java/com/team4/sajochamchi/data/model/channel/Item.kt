package com.team4.sajochamchi.data.model.channel

import com.team4.sajochamchi.data.model.SaveChannel

data class Item(
    val etag: String?,
    val id: String?,
    val kind: String?,
    val snippet: Snippet?,
)

fun Item.toSaveChannel() = SaveChannel(
    id = id,
    title = snippet?.title,
    desciptions = snippet?.description,
    thumbnailUrl = snippet?.thumbnails?.high?.url
)
