package com.aldev.adaberita.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticlesItem(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: Source? = null,
    val title: String,
    val url: String? = null,
    val content: String? = null
) : Parcelable