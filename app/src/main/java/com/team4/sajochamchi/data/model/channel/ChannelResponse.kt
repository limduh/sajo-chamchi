package com.team4.sajochamchi.data.model.channel

data class ChannelResponse(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)