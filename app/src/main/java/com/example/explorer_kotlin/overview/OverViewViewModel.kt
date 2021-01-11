package com.example.explorer_kotlin.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.SpaceResponse
import com.example.explorer_kotlin.network.NasaApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class SearchQueryStatus{ LOADING, ERROR, DONE}
class OverViewViewModel : ViewModel(){

    private val _response = MutableLiveData<List<Item>>()
    val response : LiveData<List<Item>>
    get() = _response

    private val _status = MutableLiveData<SearchQueryStatus>()
    val status: LiveData<SearchQueryStatus>
    get() = _status


    private val _navigateToSelectedResult = MutableLiveData<Item>()
    val navigateToSelectedResult: LiveData<Item>
        get() = _navigateToSelectedResult

    init {
        getSearchResponse("earth", "image")
    }


    fun displayResultDetails(item: Item)
    {
        _navigateToSelectedResult.value = item
    }

    fun displayResultDetailsComplete()
    {
        _navigateToSelectedResult.value = null
    }

    private fun getSearchResponse(s: String, mediaType: String) {
        Log.d("OverviewViewModel", "getSearchResponse called")
        viewModelScope.launch {
            _status.value = SearchQueryStatus.LOADING
            try {
                val responseFromApi: SpaceResponse =  NasaApi.RetrofitService.getSearchResults(s, mediaType)
                _response.value = responseFromApi.collection.items
                _status.value = SearchQueryStatus.DONE
                Log.d("OverviewViewModel", " responseFromApi : " + responseFromApi.toString())
                Log.d("OverviewViewModel", "size of response is : " + response.value!!.size)
            }catch (ex: Exception)
            {
                Log.d("OverviewViewModel", "in catch block ex is  : " + ex.message)
               _status.value = SearchQueryStatus.ERROR
                _response.value = ArrayList<Item>()
            }

        }

    }

}