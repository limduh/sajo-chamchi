package com.team4.sajochamchi.data.model.channel

data class Snippet(
    val customUrl: String?,
    val defaultLanguage: String?,
    val description: String?,
    val localized: Localized?,
    val publishedAt: String?,
    val thumbnails: Thumbnails?,
    val title: String?
)