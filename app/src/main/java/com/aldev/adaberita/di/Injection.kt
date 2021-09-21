package com.aldev.adaberita.di

import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(): NewsRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return NewsRepository.getInstance(remoteDataSource)
    }
}