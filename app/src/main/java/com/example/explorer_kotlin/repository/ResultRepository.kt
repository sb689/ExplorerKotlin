package com.example.explorer_kotlin.repository

import android.content.Context
import android.util.Log
import android.util.NoSuchPropertyException
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.explorer_kotlin.R
import com.example.explorer_kotlin.database.ResultDatabase
import com.example.explorer_kotlin.database.asDomainModel
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.NetworkResultContainer
import com.example.explorer_kotlin.model.asDatabaseModel
import com.example.explorer_kotlin.network.NasaApi
import com.example.explorer_kotlin.network.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ResultRepository (private val database: ResultDatabase){

    val resultItems : LiveData<List<Item>> = Transformations.map(database.itemDao.getResults()){
        it.asDomainModel()
    }

    suspend fun refreshResults(query: String?, startYear: String?, endYear:String?, context: Context)
    {
        Log.d("ResultRepository","query : ${query}, startYear: $startYear, endYear: $endYear")
        if(!Utils.isNetworkConnected(context))
            throw Exception(context.getString(R.string.no_network_error_msg))

        withContext(Dispatchers.IO){

            if(query.isNullOrEmpty() && startYear.isNullOrEmpty() && endYear.isNullOrEmpty())
            {
                if (database.itemDao.getCount() != 0)
                {
                    return@withContext
                }

            }
            val response = NasaApi.apiResponse.getSearchResults(query,
                    startYear,
                    endYear)
            Log.d("ResultRepository","response: $response")
            val networkContainer = NetworkResultContainer(response.collection.items)
            if(networkContainer.itemList.isEmpty())
            {
                throw NoSuchPropertyException(context.getString(R.string.no_data_found_error_msg))
            }

            database.itemDao.deleteAll()
            database.itemDao.insertAll(networkContainer.asDatabaseModel())
        }
    }

}