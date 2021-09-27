package com.aldev.adaberita.data

import com.aldev.adaberita.data.source.remote.RemoteDataSource
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource

class NewsRepository private constructor(private val remoteData: RemoteDataSource) :
    NewsDataSource {

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(remoteData: RemoteDataSource): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(remoteData)
            }
    }

    override suspend fun getHeadlineNews(): Resource<List<ArticlesItem>?> {
        return remoteData.getHeadlineNews()
    }

    override suspend fun getHeadlineNewsFromCategory(categoryId: String): Resource<List<ArticlesItem>?> {
        return remoteData.getHeadlineNewsFromCategory(categoryId)
    }

}