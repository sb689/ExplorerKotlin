package com.example.explorer_kotlin.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.explorer_kotlin.model.Item

class DetailViewModel(item : Item) : ViewModel() {

    private val _selectedResult = MutableLiveData<Item>()
    val selectedResult: LiveData<Item>
    get() = _selectedResult

    init {
        _selectedResult.value = item
    }

}