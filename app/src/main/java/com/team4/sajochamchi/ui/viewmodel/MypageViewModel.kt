package com.team4.sajochamchi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.video.toSaveItem
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class MypageViewModel(private val repository: TotalRepository) : ViewModel() {

    companion object {
        private const val TAG = "MypageViewModel"
    }

    val saveItems: LiveData<List<SaveItem>> = repository.allSaveItems.asLiveData()

    private val _saveName: MutableLiveData<String> = MutableLiveData()
    val saveName: LiveData<String>
        get() = _saveName

    private val _saveDescription: MutableLiveData<String> = MutableLiveData()
    val saveDescription: LiveData<String>
        get() = _saveDescription

    init {
        _saveName.value = repository.getNamePrefs()
        _saveDescription.value = repository.getDiscriptionPrefs()
    }

    fun saveNamePrefs(str: String) {
        _saveName.value = str
        repository.saveNamePrefs(str)
    }

    fun saveDescriptionPrefs(str: String) {
        _saveDescription.value = str
        repository.saveDescriptionPrfs(str)
    }

    fun deleteAllFavorite() {
        saveItems.value.orEmpty().forEach {
            delete(it)
        }
    }

    fun insert(saveItem: SaveItem) = viewModelScope.launch {
        repository.insert(saveItem)
    }

    fun delete(saveItem: SaveItem) = viewModelScope.launch {
        repository.delete(saveItem)
    }
}

class MyPageViewModelFactory(private val totalRepository: TotalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MypageViewModel::class.java)) {
            return MypageViewModel(totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}