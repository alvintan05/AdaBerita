package com.aldev.adaberita.data.source.local

import androidx.lifecycle.LiveData
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
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

    suspend fun removeBookmark(entity: BookmarkNewsEntity) = bookmarkDao.removeBookmark(entity)

    suspend fun checkIsNewsBookmarked(title: String): Boolean =
        bookmarkDao.checkIsNewsBookmarked(title)
}