package com.example.explorer_kotlin.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explorer_kotlin.model.SpaceResponse
import com.example.explorer_kotlin.network.NasaApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class SearchQueryStatus{ LOADING, ERROR, DONE}
class OverViewViewModel : ViewModel(){

    private val _response = MutableLiveData<SpaceResponse>()
    val response : LiveData<SpaceResponse>
    get() = _response

    private val _status = MutableLiveData<SearchQueryStatus>()
    val status: LiveData<SearchQueryStatus>
    get() = _status

    init {
        getSearchResponse("earth")
    }

    private fun getSearchResponse(s: String) {
        viewModelScope.launch {
            _status.value = SearchQueryStatus.LOADING
            try {
                _response.value = NasaApi.RetrofitService.getSearchResults(s)
                _status.value = SearchQueryStatus.DONE
            }catch (ex: Exception)
            {
               _status.value = SearchQueryStatus.ERROR
                _response.value = null
            }

        }

    }

}