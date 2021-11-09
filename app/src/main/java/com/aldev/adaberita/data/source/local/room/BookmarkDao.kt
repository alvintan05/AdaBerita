package com.aldev.adaberita.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aldev.adaberita.model.entity.BookmarkNewsEntity

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmarkNewsEntity: BookmarkNewsEntity)

    @Delete
    suspend fun removeBookmark(bookmarkNewsEntity: BookmarkNewsEntity)

    @Query("SELECT * FROM bookmark")
    fun getBookmarkList(): LiveData<List<BookmarkNewsEntity>>

    @Query("SELECT EXISTS(SELECT * FROM bookmark WHERE title = :title)")
    fun checkIsNewsBookmarked(title: String): LiveData<Boolean>
}