package com.example.explorer_kotlin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Link (val href: String) : Parcelable
