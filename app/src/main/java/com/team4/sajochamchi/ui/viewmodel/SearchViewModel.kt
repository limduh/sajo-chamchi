package com.team4.sajochamchi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.team4.sajochamchi.data.model.searchvideo.toSaveItem
import com.team4.sajochamchi.data.model.video.toSaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class SearchViewModel(val repository: TotalRepository) : ViewModel() {

    companion object{
        private const val TAG = "SearchViewModel"
    }

    fun searchVideos(q:String) = viewModelScope.launch {
        val response = repository.searchVideos(q = q)
        if (response.isSuccessful){
            response.body()?.let { body ->
                val saveItemList = body.items?.map { it.toSaveItem() }
                saveItemList?.forEach {
                    Log.d(TAG, "searchVideos: $it")
                }
            }
        }else{
            Log.d(TAG, "searchVideos.isNotSuccessful")
            Log.d(TAG, response.message())
        }
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