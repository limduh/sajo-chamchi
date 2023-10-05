package com.team4.sajochamchi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.data.model.category.toSaveCategory
import com.team4.sajochamchi.data.repository.TotalRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: TotalRepository) : ViewModel() {
    companion object {
        private const val TAG = "CategoryViewModel"
    }

    private val _selectedCategory: MutableLiveData<List<SaveCategory>> = MutableLiveData()
    val selectedCategory: LiveData<List<SaveCategory>>
        get() = _selectedCategory

    private val _unselectedCategory: MutableLiveData<List<SaveCategory>> = MutableLiveData()
    val unselectedCategory: LiveData<List<SaveCategory>>
        get() = _unselectedCategory

    init {
        getAllCategory()
    }

    private fun getAllCategory() = viewModelScope.launch {
        val response = repository.getAllCategories()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                val saveCategoryList = body.items.orEmpty().map {
                    it.toSaveCategory()
                }
                val selectedList = repository.getCateoryListPefs().toMutableList()
                val checkedArr = BooleanArray(selectedList.size)
                val unSelectedList = mutableListOf<SaveCategory>()
                saveCategoryList.forEach { category ->
                    var isIn = false
                    for ((idx, value) in selectedList.withIndex()) {
                        if (!checkedArr[idx] && category.title == value.title) {
                            if (category.id != value.title) {
                                selectedList[idx] = value.copy(id = category.id)
                            }
                            isIn = true
                            checkedArr[idx] = true
                            break
                        }
                    }
                    if (!isIn) unSelectedList.add(category)
                }
                repository.saveCateoryListPrefs(selectedList)
                _selectedCategory.value = selectedList
                _unselectedCategory.value = unSelectedList
            }
        }
    }

    fun addCategory(unselectedPosition:Int,category: SaveCategory) {
        val selectedList = selectedCategory.value.orEmpty().toMutableList()
        val unselectedList = unselectedCategory.value.orEmpty().toMutableList()
        if (unselectedPosition !in unselectedList.indices || unselectedList[unselectedPosition] != category) return
        selectedList.add(category)
        unselectedList.removeAt(unselectedPosition)
        repository.saveCateoryListPrefs(selectedList)
        _selectedCategory.value = selectedList
        _unselectedCategory.value = unselectedList
    }

    fun removeCategory(selectedPosition: Int,category: SaveCategory) {
        val selectedList = selectedCategory.value.orEmpty().toMutableList()
        val unselectedList = unselectedCategory.value.orEmpty().toMutableList()
        if (selectedPosition !in selectedList.indices || selectedList[selectedPosition] != category) return
        selectedList.removeAt(selectedPosition)
        for ((idx,value ) in unselectedList.withIndex()){
            if (value.id!! > category.id!!){
                unselectedList.add(idx,category)
                break
            }
            if (idx == unselectedList.lastIndex) unselectedList.add(category)
        }
        repository.saveCateoryListPrefs(selectedList)
        _selectedCategory.value = selectedList
        _unselectedCategory.value = unselectedList
    }

    fun onSelectedItemMove(from:Int,to:Int){
        val selectedList = selectedCategory.value.orEmpty().toMutableList()
        val item = selectedList[from]
        selectedList.removeAt(from)
        selectedList.add(to,item)
        repository.saveCateoryListPrefs(selectedList)
        _selectedCategory.value = selectedList
    }
}

class CategoryViewModelFactory(
    private val totalRepository: TotalRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(totalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}