package com.team4.sajochamchi.data.model.searchvideo

data class SearchVideoResponse(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)