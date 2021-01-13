package com.example.explorer_kotlin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.explorer_kotlin.database.ResultDatabase
import com.example.explorer_kotlin.database.asDomainModel
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.NetworkResultContainer
import com.example.explorer_kotlin.model.SpaceResponse
import com.example.explorer_kotlin.model.asDatabaseModel
import com.example.explorer_kotlin.network.NasaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResultRepository (private val database: ResultDatabase){

    val resultItems : LiveData<List<Item>> = Transformations.map(database.itemDao.getResults()){
        it.asDomainModel()
    }

    suspend fun searchResults(query: String, startYear: String, endYear:String)
    {
        Log.d("ResultRepository","inside refreshVideos()")
        withContext(Dispatchers.IO){

            val responseFromApi: SpaceResponse =  NasaApi.RetrofitService.getSearchResults(
                    query, startYear, endYear)
            val itemList:NetworkResultContainer = NetworkResultContainer( responseFromApi.collection.items)
            database.itemDao.insertAll(*itemList.asDatabaseModel())
        }
    }

}