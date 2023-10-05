package com.team4.sajochamchi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class ViewDetailViewModel(private val video : SaveItem,private val repository: TotalRepository) : ViewModel() {
    companion object {
        private const val TAG = "ViewDetailViewModel"
    }

    val isFavorite: LiveData<List<SaveItem>> = repository.isSaveItems.asLiveData()


    fun favoriteItem(video: SaveItem) = viewModelScope.launch{
        repository.insert(video)
    }

    fun deleteItem(video: SaveItem) = viewModelScope.launch {
        repository.delete(video)
    }

}

class ViewDetailViewModelFactory(private val video : SaveItem,private val totalRepository: TotalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewDetailViewModel::class.java)) {
            return ViewDetailViewModel(video,totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}