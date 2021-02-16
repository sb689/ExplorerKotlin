package com.example.explorer_kotlin.overview

import android.app.Application
import android.util.Log
import android.util.NoSuchPropertyException
import androidx.lifecycle.*
import com.example.explorer_kotlin.Event

import com.example.explorer_kotlin.database.getDatabase
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.repository.ResultRepository
import kotlinx.coroutines.launch


import java.lang.Exception


enum class ErrorType {NETWORK, NO_DATA}
enum class SearchQueryStatus{ LOADING, ERROR, DONE, NO_DATA}
class OverViewViewModel (query:String?, startYear: String?, endYear: String?, app: Application): AndroidViewModel(app){

    private val database = getDatabase(app)
    private val resultRepository = ResultRepository(database)

    private val _status = MutableLiveData<SearchQueryStatus>()
    val status: LiveData<SearchQueryStatus>
    get() = _status


    private val _navigateToSelectedResult = MutableLiveData<Event<Item>>()
    val navigateToSelectedResult: LiveData<Event<Item>>
        get() = _navigateToSelectedResult

    private val _navigateToSearchPage = MutableLiveData<Event<Unit>>()
    val navigateToSearchPage: LiveData<Event<Unit>>
    get() = _navigateToSearchPage

    val  response: LiveData<List<Item>> = resultRepository.resultItems

    private var _eventNetworkError = MutableLiveData<Event<Boolean>>()
    val eventNetworkError: LiveData<Event<Boolean>>
        get() = _eventNetworkError


    private var _noDataFound = MutableLiveData<Event<Boolean>>()
    val noDataFound: LiveData<Event<Boolean>>
    get() = _noDataFound



    init {
        Log.d("OverViewViewModel", "init called................")

       // getSearchResponse(query, startYear, endYear, app)

    }


    fun displaySearchPage(){

        _navigateToSearchPage.value = Event(Unit)
    }


    fun displayResultDetails(item: Item)
    {
        _navigateToSelectedResult.value = Event(item)
    }

}