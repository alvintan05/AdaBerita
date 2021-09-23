package com.aldev.adaberita.data.source.remote

import com.aldev.adaberita.data.source.remote.network.RetrofitServer
import com.aldev.adaberita.data.source.remote.response.ArticlesItem

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    suspend fun getHeadlineNews(): List<ArticlesItem>? {
        val response = RetrofitServer.getService().getHeadlinesNews()
        if (response.isSuccessful) return response.body()?.articles

        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

    suspend fun getHeadlineNewsFromCategory(categoryId: String): List<ArticlesItem>? {
        val response =
            RetrofitServer.getService().getHeadlinesNewsFromCategory(category = categoryId)
        if (response.isSuccessful) return response.body()?.articles

        throw Exception("Terjadi kesalahan saat melakukan request data, status error ${response.code()}")
    }

}