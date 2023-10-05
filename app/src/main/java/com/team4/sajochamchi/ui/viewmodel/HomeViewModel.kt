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
import com.team4.sajochamchi.data.model.searchvideo.toSaveItem
import com.team4.sajochamchi.data.model.video.toSaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch
import java.util.Collections.addAll

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

    private val _beforeCategory: MutableLiveData<SaveCategory> = MutableLiveData()
    val beforeCategory: LiveData<SaveCategory>
        get() = _beforeCategory


    private val _pupularState: MutableLiveData<SearchState> = MutableLiveData()
    val popularState: LiveData<SearchState>
        get() = _pupularState


    private val _categoryState: MutableLiveData<SearchState> = MutableLiveData()
    val categoryState: LiveData<SearchState>
        get() = _categoryState


    init {
        _categories.value = repository.getCateoryListPefs()
    }

    fun getCategoriesListPrefs() {
        _categories.value = repository.getCateoryListPefs()
    }


    fun getAllMostPopular() = viewModelScope.launch {
        _pupularState.value = SearchState.Loading("")
        val response = repository.getAllMostPopular()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveItem() }
                _pupularState.value = SearchState.Finish("", body.nextPageToken ?: "")
                saveItemList?.let { saveItems ->
                    _popularItemList.value = saveItems
                }
                /*saveItemList?.forEach {
                    Log.d(TAG, "getAllMostPopular: $it")
                }*/
            }
        } else {
            Log.d(TAG, "getAllMostPopular.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun getPagingAllMostPopular() = viewModelScope.launch {
        when (val state = popularState.value) {
            is SearchState.Loading -> {
                return@launch
            }

            is SearchState.Finish -> {
                _pupularState.value = SearchState.Loading("")
                val response = repository.getAllMostPopular(
                    pageToken = state.nextToken
                )
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val saveItemList = body.items?.map { it.toSaveItem() }

                        _pupularState.value =
                            SearchState.Finish(state.q, body.nextPageToken ?: "")
                        saveItemList?.let {
                            _popularItemList.value =
                                popularItemList.value.orEmpty().toMutableList().apply {
                                    addAll(it)
                                }
                        }
                    }
                } else {
                    _pupularState.value = SearchState.Finish(
                        "",
                        state.nextToken
                    )
                }
            }

            else -> {}//null
        }
    }


    fun getAllMostPopularWithCategoryId(videoCategoryId: String) = viewModelScope.launch {
        _channelItemList.value = emptyList()
        channelSet.clear()
        _categoryState.value = SearchState.Loading(videoCategoryId)
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
                _categoryState.value = SearchState.Finish(videoCategoryId, body.nextPageToken ?: "")
                saveItemList?.let { saveItems ->
                    _categoryItemList.value = saveItems
                }
            }
        } else {
            Log.d(TAG, "getAllMostPopularWithCategoryId.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun getPagingAllMostPopularWithCategoryId() = viewModelScope.launch {
        when (val state = categoryState.value) {
            is SearchState.Loading -> {
                return@launch
            }

            is SearchState.Finish -> {
                _categoryState.value = SearchState.Loading(state.q)
                val response = repository.getAllMostPopularWithCategoryId(
                    videoCategoryId = state.q,
                    pageToken = state.nextToken
                )
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


                        _categoryState.value =
                            SearchState.Finish(state.q, body.nextPageToken ?: "")
                        saveItemList?.let {
                            _categoryItemList.value =
                                categoryItemList.value.orEmpty().toMutableList().apply {
                                    addAll(it)
                                }
                        }
                    }
                } else {
                    _categoryState.value = SearchState.Finish(
                        state.q,
                        state.nextToken
                    )
                }
            }

            else -> {}//null
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
                    val currentList = channelItemList.value.orEmpty().toMutableList()
                    currentList.add(it)
                    _channelItemList.value = currentList
                }
            }
        } else {
            Log.d(TAG, "getAllCategories.isNotSuccessful")
            Log.d(TAG, response.message())
        }
    }

    fun setCurrentCategory(category: SaveCategory) {
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