package com.aldev.adaberita.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem

interface NewsDataSource {
    // Remote
    suspend fun getHeadlineNews(): LiveData<PagingData<BookmarkNewsEntity>>
    suspend fun getHeadlineNewsFromCategory(categoryId: String): LiveData<PagingData<BookmarkNewsEntity>>

    // Local
    fun getBookmarkList(): LiveData<List<BookmarkNewsEntity>>
    suspend fun addBookmark(entity: BookmarkNewsEntity)
    suspend fun removeBookmark(entity: BookmarkNewsEntity)
    fun checkIsNewsBookmarked(title: String): LiveData<Boolean>
}