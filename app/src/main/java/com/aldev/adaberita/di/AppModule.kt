package com.aldev.adaberita.di

import android.content.Context
import com.aldev.adaberita.data.source.local.LocalDataSource
import com.aldev.adaberita.data.source.local.room.BookmarkDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Singleton
    @Provides
    fun provideLocalDataSource(): LocalDataSource {
        val database = BookmarkDatabase.getInstance(context)
        return LocalDataSource.getInstance(database.bookmarkDao())
    }
}