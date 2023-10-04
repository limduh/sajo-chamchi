package com.team4.sajochamchi.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.team4.sajochamchi.data.api.RetrofitInstance
import com.team4.sajochamchi.data.api.RetrofitInstance.api
import com.team4.sajochamchi.data.db.ItemDatabase
import com.team4.sajochamchi.data.model.SaveCategory
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

    //use retrofit
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


    //Room
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


    // use sharedpreference
    override fun getCateoryListPefs(): List<SaveCategory> {
        return mySharedPreferences.getCateoryListPefs()
    }

    override fun saveCateoryListPrefs(list: List<SaveCategory>) {
        mySharedPreferences.saveCateoryListPrefs(list)
    }

    override fun getSearchHistoryListPefs(): List<String> {
        return mySharedPreferences.getSearchHistoryListPefs()
    }

    override fun saveSearchHistoryListPrefs(list: List<String>) {
        mySharedPreferences.saveSearchHistoryListPrefs(list)
    }


    override fun getNamePrefs(): String{
        return mySharedPreferences.getNamePrefs()
    }
    override fun getDiscriptionPrefs(): String{
        return  mySharedPreferences.getDiscriptionPrefs()
    }
    override fun saveNamePrefs(str: String){
        mySharedPreferences.saveNamePrefs(str)
    }
    override fun saveDescriptionPrfs(str: String){
        mySharedPreferences.saveDescriptionPrfs(str)
    }
}