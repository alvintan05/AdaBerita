package com.aldev.adaberita.data

import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource

interface NewsDataSource {
    suspend fun getHeadlineNews(): Resource<List<ArticlesItem>?>
    suspend fun getHeadlineNewsFromCategory(categoryId: String): Resource<List<ArticlesItem>?>
    suspend fun getBookmarkList(): Resource<List<BookmarkNewsEntity>>
    suspend fun addBookmark(entity: BookmarkNewsEntity)
    suspend fun removeBookmark(id: Int)
}