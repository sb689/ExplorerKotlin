package com.example.explorer_kotlin.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.explorer_kotlin.database.getDatabase
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.repository.ResultRepository

class DetailViewModel(item : Item, app: Application) : ViewModel() {

    private val database = getDatabase(app)
    private val resultRepository = ResultRepository(database)

    private val _selectedResult = MutableLiveData<Item>()
    val selectedResult: LiveData<Item>
    get() = _selectedResult

    init {
        _selectedResult.value = item
    }
    val  savedData: LiveData<List<Item>> = resultRepository.resultItems

    fun changeDisplayedData(item: Item){
        _selectedResult.value = item
    }


}