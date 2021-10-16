package com.aldev.adaberita.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import com.aldev.adaberita.utils.Resource

interface NewsDataSource {
    suspend fun getHeadlineNews(): LiveData<PagingData<ArticlesItem>>
    suspend fun getHeadlineNewsFromCategory(categoryId: String): Resource<List<ArticlesItem>>
    suspend fun getBookmarkList(): Resource<List<BookmarkNewsEntity>>
    suspend fun addBookmark(entity: BookmarkNewsEntity)
    suspend fun removeBookmark(id: Int)
}