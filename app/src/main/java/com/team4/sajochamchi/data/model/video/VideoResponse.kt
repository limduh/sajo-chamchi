package com.team4.sajochamchi.data.model.video

data class VideoResponse(
    //val etag: String,
    val items: List<Item>?,
    // API 리소스의 유형을 식별합니다.
    // 값은 youtube#videoListResponse입니다.
    val kind: String?,
    val nextPageToken: String?,
    val pageInfo: PageInfo?,
    val prevPageToken: String?
)