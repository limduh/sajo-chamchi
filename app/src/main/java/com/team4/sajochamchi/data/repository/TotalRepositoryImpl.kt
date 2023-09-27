package com.team4.sajochamchi.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.team4.sajochamchi.data.api.RetrofitInstance
import com.team4.sajochamchi.data.api.RetrofitInstance.api
import com.team4.sajochamchi.data.db.ItemDatabase
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.category.CategoryResponse
import com.team4.sajochamchi.data.model.channel.ChannelResponse
import com.team4.sajochamchi.data.model.searchvideo.SearchVideoResponse
import com.team4.sajochamchi.data.model.video.VideoResponse
import com.team4.sajochamchi.data.sharedpreferences.MySharedPreferences
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class TotalRepositoryImpl(context: Context) : TotalRepository {
    private val mySharedPreferences: MySharedPreferences = MySharedPreferences(context)
    private val database = ItemDatabase.getDatabase(context)
    private val dao = database.getItemDao()
    //use api
    override suspend fun getAllMostPopular(pageToken: String): Response<VideoResponse> {
        return api.getAllMostPopular(pageToken = pageToken)
    }

    override suspend fun getAllMostPopularWithCategoryId(
        videoCategoryId: String,
        pageToken: String,
    ): Response<VideoResponse> {
        return api.getAllMostPopularWithCategoryId(
            videoCategoryId = videoCategoryId,
            pageToken = pageToken
        )
    }

    override suspend fun getAllCategories(): Response<CategoryResponse> {
        return api.getAllCategories()
    }

    override suspend fun getChannelWithId(id: String): Response<ChannelResponse> {
        return api.getChannelWithId(id = id)
    }

    override suspend fun searchVideos(q: String, pageToken: String): Response<SearchVideoResponse> {
        return api.searchVideos(q = q, pageToken = pageToken)
    }


    override val allSaveItems: Flow<List<SaveItem>> = dao.getAll()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(saveItem: SaveItem) {
        dao.insert(saveItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun delete(saveItem: SaveItem) {
        dao.delete(saveItem)
    }
}