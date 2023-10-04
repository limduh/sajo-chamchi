package com.team4.sajochamchi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.video.toSaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class MypageViewModel(private val repository: TotalRepository) : ViewModel() {

    companion object{
        private const val TAG = "MypageViewModel"
    }

    val saveItems : LiveData<List<SaveItem>> = repository.allSaveItems.asLiveData()

    fun insert(saveItem: SaveItem) = viewModelScope.launch {
        repository.insert(saveItem)
    }

    fun delete(saveItem: SaveItem) = viewModelScope.launch {
        repository.delete(saveItem)
    }
}

class MyPageViewModelFactory(private val totalRepository: TotalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MypageViewModel::class.java)){
            return MypageViewModel(totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}