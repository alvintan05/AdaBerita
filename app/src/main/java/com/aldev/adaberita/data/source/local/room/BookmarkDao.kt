package com.aldev.adaberita.data.source.local.room

import androidx.room.*
import com.aldev.adaberita.model.entity.BookmarkNewsEntity

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmarkNewsEntity: BookmarkNewsEntity)

    @Query("DELETE FROM bookmark WHERE id = :id")
    suspend fun removeBookmark(id: Int)

    @Query("SELECT * FROM bookmark")
    suspend fun getBookmarkList(): List<BookmarkNewsEntity>
}