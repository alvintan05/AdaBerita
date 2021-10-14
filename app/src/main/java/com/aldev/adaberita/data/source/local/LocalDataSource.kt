package com.aldev.adaberita.data.source.local

import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.local.room.BookmarkDao
import com.aldev.adaberita.utils.Resource
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val bookmarkDao: BookmarkDao) {

    suspend fun getBookmarkList(): Resource<List<BookmarkNewsEntity>> {
        val data = bookmarkDao.getBookmarkList()
        return if (data.isNotEmpty()) {
            Resource.Success(data)
        } else {
            Resource.Empty()
        }
    }

    suspend fun addBookmark(entity: BookmarkNewsEntity) = bookmarkDao.addBookmark(entity)

    suspend fun removeBookmark(id: Int) = bookmarkDao.removeBookmark(id)
}