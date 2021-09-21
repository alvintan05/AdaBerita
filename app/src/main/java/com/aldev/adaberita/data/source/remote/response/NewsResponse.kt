package com.aldev.adaberita.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val totalResults: Int? = null,
    val articles: List<ArticlesItem>,
    val status: String? = null
) : Parcelable