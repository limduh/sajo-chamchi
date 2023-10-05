package com.team4.sajochamchi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.data.model.SaveChannel
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.category.toSaveCategory
import com.team4.sajochamchi.data.model.channel.toSaveChannel
import com.team4.sajochamchi.data.model.video.toSaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TotalRepository) : ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _popularItemList: MutableLiveData<List<SaveItem>> = MutableLiveData()
    val popularItemList: LiveData<List<SaveItem>>
        get() = _popularItemList

    private val _categories: MutableLiveData<List<SaveCategory>> = MutableLiveData()
    val categories: LiveData<List<SaveCategory>>
        get() = _categories

    private val _categoryItemList: MutableLiveData<List<SaveItem>> = MutableLiveData()
    val categoryItemList: LiveData<List<SaveItem>>
        get() = _categoryItemList

    private val channelSet: MutableSet<String> = mutableSetOf()

    private val _channelItemList: MutableLiveData<List<SaveChannel>> = MutableLiveData()
    val channelItemList: LiveData<List<SaveChannel>>
        get() = _channelItemList

    private val _beforeCategory : MutableLiveData<SaveCategory> = MutableLiveData()
    val beforeCategory : LiveData<SaveCategory>
        get() = _beforeCategory

    init {
        _categories.value = repository.getCateoryListPefs()
    }

    fun getCategoriesListPrefs(){
        _categories.value = repository.getCateoryListPefs()
    }

    fun getAllMostPopular() = viewModelScope.launch {
        val response = repository.getAllMostPopular()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveItem() }
                saveItemList?.let { saveItems ->
                    _popularItemList.value = saveItems
                }
                saveItemList?.forEach {
                    Log.d(TAG, "getAllMostPopular: $it")
                }
            }
        } else {
            Log.d(TAG, "getAllMostPopular.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun getAllMostPopularWithCategoryId(videoCategoryId: String) = viewModelScope.launch {
        _channelItemList.value = emptyList()
        channelSet.clear()
        val response = repository.getAllMostPopularWithCategoryId(videoCategoryId = videoCategoryId)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveItem() }
                saveItemList?.forEach {
                    Log.d(TAG, "getAllMostPopularWithCategoryId: $it")
                    if (it.channelId != null && it.channelId !in channelSet) {
                        channelSet.add(it.channelId)
                        getChannelWithId(it.channelId)
                    }
                }
                saveItemList?.let { saveItems ->
                    _categoryItemList.value = saveItems
                }
            }
        } else {
            Log.d(TAG, "getAllMostPopularWithCategoryId.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    /*fun getAllCategories() = viewModelScope.launch {
        val response = repository.getAllCategories()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveCategory() }
                saveItemList?.forEach {
                    Log.d(TAG, "getAllCategories: $it")
                }
            }
        } else {
            Log.d(TAG, "getAllCategories.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }*/

    private fun getChannelWithId(id: String) = viewModelScope.launch {
        val response = repository.getChannelWithId(id)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveChannel() }
                saveItemList?.forEach {
                    Log.d(TAG, "getChannelWithId: $it")
                    val currentList =  channelItemList.value.orEmpty().toMutableList()
                    currentList.add(it)
                    _channelItemList.value = currentList
                }
            }
        } else {
            Log.d(TAG, "getAllCategories.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun setCurrentCategory(category: SaveCategory){
        _beforeCategory.value = category
    }
}

class HomeViewModelFactory(private val totalRepository: TotalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}