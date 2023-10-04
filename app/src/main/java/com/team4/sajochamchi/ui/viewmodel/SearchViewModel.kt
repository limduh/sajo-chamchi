package com.team4.sajochamchi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.searchvideo.toSaveItem
import com.team4.sajochamchi.data.model.video.toSaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: TotalRepository) : ViewModel() {

    companion object{
        private const val TAG = "SearchViewModel"
    }

    private val _searchHistory : MutableLiveData<List<String>> = MutableLiveData()
    val searchHistory : LiveData<List<String>>
        get() = _searchHistory

    private val _searchResult : MutableLiveData<List<SaveItem>> = MutableLiveData()
    val searchResult : LiveData<List<SaveItem>>
        get() = _searchResult

    init {
        _searchHistory.value = repository.getSearchHistoryListPefs()
    }

    fun searchVideos(q:String) = viewModelScope.launch {
        val response = repository.searchVideos(q = q)
        if (response.isSuccessful){
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveItem() }
                saveItemList?.forEach {
                    Log.d(TAG, "searchVideos: $it")
                }
                saveItemList?.let { _searchResult.value = it }
            }
        }else{
            Log.d(TAG, "searchVideos.isNotSuccessful")
            Log.d(TAG, response.message())
            _searchResult.value = emptyList()
        }
    }

    fun addSearchHistory(string: String) {
        val history = searchHistory.value.orEmpty().toMutableList()
        val idx = history.indexOfFirst { it == string }
        val arrayList = ArrayList<String>()
        if (idx >= 0) { // 히스토리에 있음
            history.removeAt(idx)
        }
        arrayList.add(string)
        arrayList.addAll(history)
        repository.saveSearchHistoryListPrefs(arrayList)
        _searchHistory.value = arrayList
    }

    fun deleteSearchHistory(position : Int){
        val history = searchHistory.value.orEmpty().toMutableList()
        history.removeAt(position)
        repository.saveSearchHistoryListPrefs(history)
        _searchHistory.value = history
    }

}

class SearchViewModelFactory(private val totalRepository: TotalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}