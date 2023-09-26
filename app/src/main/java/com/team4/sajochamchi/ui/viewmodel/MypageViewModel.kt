package com.team4.sajochamchi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team4.sajochamchi.data.repository.TotalRepository

class MypageViewModel(val repository: TotalRepository) : ViewModel() {

}

class MyPageViewModelFactory(private val totalRepository: TotalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MypageViewModel::class.java)){
            return MypageViewModel(totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}