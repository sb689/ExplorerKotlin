package com.example.explorer_kotlin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item (
        val data: MutableList<Data>,
        val links: MutableList<Link>

        ) : Parcelable