package com.aldev.adaberita.data.source.local

import androidx.lifecycle.LiveData
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.local.room.BookmarkDao
import com.aldev.adaberita.utils.Resource
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val bookmarkDao: BookmarkDao) {

    fun getBookmarkList(): LiveData<List<BookmarkNewsEntity>> {
        return bookmarkDao.getBookmarkList()
    }

    suspend fun addBookmark(entity: BookmarkNewsEntity) = bookmarkDao.addBookmark(entity)

    suspend fun removeBookmark(entity: BookmarkNewsEntity) = bookmarkDao.removeBookmark(entity)

    fun checkIsNewsBookmarked(title: String): LiveData<Boolean> =
        bookmarkDao.checkIsNewsBookmarked(title)
}