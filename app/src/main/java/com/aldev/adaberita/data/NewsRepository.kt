package com.aldev.adaberita.data

import com.aldev.adaberita.data.source.local.LocalDataSource
import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.remote.RemoteDataSource
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : NewsDataSource {

    override suspend fun getHeadlineNews(): Resource<List<ArticlesItem>> =
        remoteDataSource.getHeadlineNews()

    override suspend fun getHeadlineNewsFromCategory(categoryId: String): Resource<List<ArticlesItem>> =
        remoteDataSource.getHeadlineNewsFromCategory(categoryId)

    override suspend fun getBookmarkList(): Resource<List<BookmarkNewsEntity>> =
        localDataSource.getBookmarkList()

    override suspend fun addBookmark(entity: BookmarkNewsEntity) {
        localDataSource.addBookmark(entity)
    }

    override suspend fun removeBookmark(id: Int) {
        localDataSource.removeBookmark(id)
    }

}