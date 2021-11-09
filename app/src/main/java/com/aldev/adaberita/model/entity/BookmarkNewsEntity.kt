package com.aldev.adaberita.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "bookmark")
@Parcelize
data class BookmarkNewsEntity(
    @PrimaryKey
    val title: String,

    @ColumnInfo(name = "published_at")
    val publishedAt: String? = null,
    val author: String? = null,

    @ColumnInfo(name = "url_image")
    val urlToImage: String? = null,
    val description: String? = null,
    val sourceName: String? = null,
    val sourceId: String? = null,
    val url: String? = null,
    val content: String? = null
) : Parcelable