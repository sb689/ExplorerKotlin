package com.example.explorer_kotlin.search

import androidx.lifecycle.*
import com.example.explorer_kotlin.model.Item

class SearchViewModel : ViewModel() {

    private val _displaySearchResults = MutableLiveData<Boolean>()
    val displaySearchResults: LiveData<Boolean>
        get() = _displaySearchResults


    fun searchResultDisplayComplete(){
        _displaySearchResults.value = false
    }

    fun btnSearchClicked(){
        _displaySearchResults.value = true;
    }



}