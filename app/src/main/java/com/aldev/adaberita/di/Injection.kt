package com.aldev.adaberita.di

import android.content.Context
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.data.source.local.LocalDataSource
import com.aldev.adaberita.data.source.local.room.BookmarkDatabase
import com.aldev.adaberita.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val database = BookmarkDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.bookmarkDao())
        return NewsRepository.getInstance(remoteDataSource, localDataSource)
    }
}