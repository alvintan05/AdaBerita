package com.aldev.adaberita.di

import android.content.Context
import com.aldev.adaberita.data.source.local.room.BookmarkDao
import com.aldev.adaberita.data.source.local.room.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBookmarkDao(@ApplicationContext context: Context): BookmarkDao {
        return BookmarkDatabase.getInstance(context).bookmarkDao()
    }

}