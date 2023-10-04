package com.team4.sajochamchi.data.repository

import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.category.CategoryResponse
import com.team4.sajochamchi.data.model.channel.ChannelResponse
import com.team4.sajochamchi.data.model.searchvideo.SearchVideoResponse
import com.team4.sajochamchi.data.model.video.VideoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TotalRepository {

    // Retrofit
    suspend fun getAllMostPopular(pageToken: String = ""): Response<VideoResponse>
    suspend fun getAllMostPopularWithCategoryId(
        videoCategoryId: String,
        pageToken: String = "",
    ): Response<VideoResponse>

    suspend fun getAllCategories(): Response<CategoryResponse>
    suspend fun getChannelWithId(id: String): Response<ChannelResponse>
    suspend fun searchVideos(q: String, pageToken: String = "") : Response<SearchVideoResponse>

    // Room
    val allSaveItems : Flow<List<SaveItem>>
    suspend fun insert(saveItem: SaveItem)
    suspend fun delete(saveItem: SaveItem)

    // SharedPreference
    fun getCateoryListPefs(): List<SaveCategory>
    fun saveCateoryListPrefs(list: List<SaveCategory>)
    fun getSearchHistoryListPefs(): List<String>
    fun saveSearchHistoryListPrefs(list: List<String>)


    fun getNamePrefs(): String
    fun getDiscriptionPrefs(): String
    fun saveNamePrefs(str: String)
    fun saveDescriptionPrfs(str: String)
}