package com.example.explorer_kotlin.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Item (
        val data: List<Data>,
        val links: List<Link>

        ) : Parcelable