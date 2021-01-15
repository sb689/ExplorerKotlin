package com.example.explorer_kotlin.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService

class Utils {

    companion object {
        fun isNetworkConnected(context: Context): Boolean {

            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            val currentNetwork = connectivityManager.activeNetwork
            return currentNetwork != null

        }
    }
}