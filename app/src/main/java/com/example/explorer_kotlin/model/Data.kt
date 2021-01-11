package com.example.explorer_kotlin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data (

        val date_created: String,
        val description: String,
        val title: String,
        val location: String,
        val secondary_creator: String,
        val nasa_id: String
) : Parcelable