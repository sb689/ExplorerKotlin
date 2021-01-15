package com.example.explorer_kotlin.overview

import android.app.Application
import android.util.Log
import android.util.NoSuchPropertyException
import androidx.lifecycle.*
import com.example.explorer_kotlin.database.getDatabase
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.SpaceResponse
import com.example.explorer_kotlin.network.NasaApi
import com.example.explorer_kotlin.repository.ResultRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import kotlin.math.log

enum class ErrorType {NETWORK, NO_DATA}
enum class SearchQueryStatus{ LOADING, ERROR, DONE}
class OverViewViewModel (query:String?, startYear: String?, endYear: String? , app:Application): AndroidViewModel(app){

    private val database = getDatabase(app)
    private val resultRepository = ResultRepository(database)

    private val _status = MutableLiveData<SearchQueryStatus>()
    val status: LiveData<SearchQueryStatus>
    get() = _status


    private val _navigateToSelectedResult = MutableLiveData<Item>()
    val navigateToSelectedResult: LiveData<Item>
        get() = _navigateToSelectedResult

    private val _navigateToSearchPage = MutableLiveData<Boolean>()
    val navigateToSearchPage: LiveData<Boolean>
    get() = _navigateToSearchPage

    val  response: LiveData<List<Item>> = resultRepository.resultItems

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

//    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
//
//    val isNetworkErrorShown: LiveData<Boolean>
//        get() = _isNetworkErrorShown

    private var _noDataFound = MutableLiveData<Boolean>(false)
    val noDataFound: LiveData<Boolean>
    get() = _noDataFound



    init {
        getSearchResponse(query, startYear, endYear)

    }

    fun noDataFoundErrorShown(){
        _noDataFound.value = false
    }

    fun displaySearchPage(){
        _navigateToSearchPage.value = true
    }

    fun displaySearchPageComplete()
    {
        _navigateToSearchPage.value = false
    }

    fun displayResultDetails(item: Item)
    {
        _navigateToSelectedResult.value = item
    }

    fun displayResultDetailsComplete()
    {

        _navigateToSelectedResult.value = null
    }


    private fun getSearchResponse(
            query: String?,
                                  startYear: String?,
                                  endYear: String?
    ) {
        viewModelScope.launch {
            try {
                resultRepository.refreshResults(query, startYear, endYear, getApplication())
                _eventNetworkError.value = false
               // _isNetworkErrorShown.value = false

            } catch (networkError: Exception) {
                // Show a Toast error message and hide the progress bar.
                if(networkError is NoSuchPropertyException)
                    _noDataFound.value = true
                else
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _eventNetworkError.value = false
    }


}