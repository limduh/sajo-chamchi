package com.team4.sajochamchi.data.model.category

import com.team4.sajochamchi.data.model.SaveCategory

data class Item(
    val etag: String?,
    val id: String?,
    val kind: String?,
    val snippet: Snippet?,
)

fun Item.toSaveCategory() = SaveCategory(
    id = id,
    title = snippet?.title
)