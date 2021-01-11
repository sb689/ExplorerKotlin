package com.example.explorer_kotlin.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class OverviewViewModelFactory(
        private val query: String?,
        private val startYear: String?,
        private val endYear: String?,
        private val app: Application,
):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverViewViewModel::class.java)) {
            return OverViewViewModel(query, startYear, endYear, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}