package com.team4.sajochamchi.data.repository

import com.team4.sajochamchi.data.model.category.CategoryResponse
import com.team4.sajochamchi.data.model.channel.ChannelResponse
import com.team4.sajochamchi.data.model.searchvideo.SearchVideoResponse
import com.team4.sajochamchi.data.model.video.VideoResponse
import retrofit2.Response

interface TotalRepository {
    suspend fun getAllMostPopular(pageToken: String = ""): Response<VideoResponse>
    suspend fun getAllMostPopularWithCategoryId(
        videoCategoryId: String,
        pageToken: String = "",
    ): Response<VideoResponse>

    suspend fun getAllCategories(): Response<CategoryResponse>
    suspend fun getChannelWithId(id: String): Response<ChannelResponse>
    suspend fun searchVideos(q: String, pageToken: String = "") : Response<SearchVideoResponse>

}