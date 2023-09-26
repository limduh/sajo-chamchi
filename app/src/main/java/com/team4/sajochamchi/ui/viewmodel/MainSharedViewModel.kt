package com.team4.sajochamchi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainSharedViewModel : ViewModel() {
    //bottom nav bar 위치 저장용
    private val _bottomMenu : MutableLiveData<MainSharedBottomMenu> = MutableLiveData()
    val bottomMenu : LiveData<MainSharedBottomMenu>
        get() = _bottomMenu

    //homeFragment에 동작이 필요한경우
    private val _homeEvent : MutableLiveData<MainSharedEventForHome> = MutableLiveData()
    val homeEvent : LiveData<MainSharedEventForHome>
        get() = _homeEvent

    //searchFragment에 동작이 필요한경우
    private val _searchEvent : MutableLiveData<MainSharedEventForSearch> = MutableLiveData()
    val searchEvent : LiveData<MainSharedEventForSearch>
        get() = _searchEvent

    //myPageFragment에 동작이 필요한경우
    private val _myPageEvent : MutableLiveData<MainSharedEventForMyPage> = MutableLiveData()
    val myPageEvent : LiveData<MainSharedEventForMyPage>
        get() = _myPageEvent

    init {
        updateBottomMenuPos(0)
    }

    fun updateBottomMenuPos(postion: Int){
        _bottomMenu.value = MainSharedBottomMenu.UpdateBottomMenuPosition(postion)
    }

}

sealed interface MainSharedBottomMenu {
    data class UpdateBottomMenuPosition(
        val postion :Int
    ) : MainSharedBottomMenu
}

sealed interface MainSharedEventForHome{}
sealed interface MainSharedEventForSearch{}
sealed interface MainSharedEventForMyPage{}
