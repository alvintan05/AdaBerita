package com.aldev.adaberita.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "published_at")
    val publishedAt: String? = null,
    val author: String? = null,

    @ColumnInfo(name = "url_image")
    val urlToImage: String? = null,
    val description: String? = null,
    val sourceName: String? = null,
    val sourceId: String? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
)