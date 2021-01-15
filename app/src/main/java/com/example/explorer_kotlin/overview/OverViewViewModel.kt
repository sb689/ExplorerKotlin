package com.example.explorer_kotlin.overview

import android.app.Application
import android.util.Log
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

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
        getSearchResponse(query, startYear, endYear)

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
//
//    private fun getSearchResponse(query: String?,
//                                  startYear: String?,
//                                  endYear: String?
//    ) {
//        Log.d("OverviewViewModel", "getSearchResponse called")
//        viewModelScope.launch {
//
//
//            _status.value = SearchQueryStatus.LOADING
//            try {
//                resultRepository.refreshResults(query, startYear, endYear)
//                _status.value = SearchQueryStatus.DONE
//                Log.d("OverviewViewModel", "size of response is : " + response.value!!.size)
//            }catch (ex: Exception)
//            {
//                Log.d("OverviewViewModel", "in catch block ex is  : " + ex.message)
//               _status.value = SearchQueryStatus.ERROR
//
//            }
//        }
//    }

    private fun getSearchResponse(
            query: String?,
                                  startYear: String?,
                                  endYear: String?
    ) {
        viewModelScope.launch {
            try {
                resultRepository.refreshResults(query, startYear, endYear)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(response.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


}