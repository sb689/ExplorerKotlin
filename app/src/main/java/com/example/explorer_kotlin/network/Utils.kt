package com.example.explorer_kotlin.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.util.NoSuchPropertyException
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewModelScope
import com.example.explorer_kotlin.Event
import com.example.explorer_kotlin.database.getDatabase
import com.example.explorer_kotlin.overview.SearchQueryStatus
import com.example.explorer_kotlin.repository.ResultRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class Utils {

    companion object {
        fun isNetworkConnected(context: Context): Boolean {

            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            val currentNetwork = connectivityManager.activeNetwork
            return currentNetwork != null

        }


    }

}