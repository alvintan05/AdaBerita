package com.aldev.adaberita.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val totalResults: Int? = null,
    val articles: MutableList<ArticlesItem>,
    val status: String? = null
) : Parcelable