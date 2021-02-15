package com.example.explorer_kotlin.search

import androidx.lifecycle.*
import com.example.explorer_kotlin.Event
import com.example.explorer_kotlin.model.Item

class SearchViewModel : ViewModel() {

    private val _displaySearchResults = MutableLiveData<Event<Unit>>()
    val displaySearchResults: LiveData<Event<Unit>>
        get() = _displaySearchResults


    fun btnSearchClicked(){
        _displaySearchResults.value = Event(Unit)
    }



}