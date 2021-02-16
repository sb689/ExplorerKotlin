package com.example.explorer_kotlin.search

import android.app.Application
import android.util.Log
import android.util.NoSuchPropertyException
import androidx.lifecycle.*
import com.example.explorer_kotlin.Event
import com.example.explorer_kotlin.overview.SearchQueryStatus
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.database.getDatabase
import com.example.explorer_kotlin.repository.ResultRepository
import kotlinx.coroutines.launch


class SearchViewModel(app:Application) : AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val resultRepository = ResultRepository(database)
    // Two-way dataBinding, exposing MutableLiveData
    val query = MutableLiveData<String>()
    val startYear = MutableLiveData<String>()
    val endYear = MutableLiveData<String>()
    var resultStatus = SearchQueryStatus.DONE

    private val _displaySearchResults = MutableLiveData<Event<Unit>>()
    val displaySearchResults: LiveData<Event<Unit>>
        get() = _displaySearchResults


    private val _toastText = MutableLiveData<Event<Int>>()
    val toastText: LiveData<Event<Int>> = _toastText

    private val _hideKeyBoard = MutableLiveData<Event<Boolean>>()
    val hideKeyBoard: LiveData<Event<Boolean>>
    get() = _hideKeyBoard

    fun btnSearchClicked(){

        _hideKeyBoard.value = Event(true)
         getSearchResponse(query.value, startYear.value, endYear.value, getApplication())

    }



    private fun getSearchResponse(
            query: String?,
            startYear: String?,
            endYear: String?,
            app : Application
    ) {
        resultStatus = SearchQueryStatus.LOADING

        viewModelScope.launch {

            try {
                resultRepository.refreshResults(query, startYear, endYear, app)
                resultStatus = SearchQueryStatus.DONE
                _displaySearchResults.value = Event(Unit)

            } catch (networkError: Exception) {

                // Show a snackBar and hide the animation
                if(networkError is NoSuchPropertyException) {
                    resultStatus = SearchQueryStatus.NO_DATA
                    _toastText.value = Event(  R.string.no_data_found_error_msg)
                    Log.d("SearchViewModel", "snackbarText set to empty_task_message")
                }
                else {
                    resultStatus = SearchQueryStatus.ERROR
                    _toastText.value = Event(  R.string.no_network_error_msg)
                }
            }
        }
    }

}