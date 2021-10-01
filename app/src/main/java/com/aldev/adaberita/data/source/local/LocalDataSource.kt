package com.aldev.adaberita.data.source.local

import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.data.source.local.room.BookmarkDao
import com.aldev.adaberita.utils.Resource
import com.aldev.adaberita.utils.Status
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(bookmarkDao: BookmarkDao): LocalDataSource =
            instance ?: LocalDataSource(bookmarkDao)
    }

    suspend fun getBookmarkList(): Resource<List<BookmarkNewsEntity>> {
        val data = bookmarkDao.getBookmarkList()
        return if (data.isNotEmpty()) {
            Resource(Status.SUCCESS, data, null)
        } else {
            Resource(Status.EMPTY, null, "Belum ada data berita yang tersimpan")
        }
    }

    suspend fun addBookmark(entity: BookmarkNewsEntity) = bookmarkDao.addBookmark(entity)

    suspend fun removeBookmark(id: Int) = bookmarkDao.removeBookmark(id)
}