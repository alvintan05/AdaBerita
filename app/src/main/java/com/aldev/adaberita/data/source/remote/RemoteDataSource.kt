package com.aldev.adaberita.data.source.remote

import com.aldev.adaberita.data.source.remote.network.RetrofitServer
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import com.aldev.adaberita.utils.Status
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

//    companion object {
//        @Volatile
//        private var instance: RemoteDataSource? = null
//
//        fun getInstance(): RemoteDataSource =
//            instance ?: synchronized(this) {
//                instance ?: RemoteDataSource()
//            }
//    }

    suspend fun getHeadlineNews(): Resource<List<ArticlesItem>?> {
        val response = RetrofitServer.getService().getHeadlinesNews()
        return if (response.isSuccessful) Resource(Status.SUCCESS, response.body()?.articles, null)
        else Resource(
            Status.ERROR,
            null,
            "Terjadi kesalahan saat melakukan request data, status error ${response.code()}"
        )
    }

    suspend fun getHeadlineNewsFromCategory(categoryId: String): Resource<List<ArticlesItem>?> {
        val response =
            RetrofitServer.getService().getHeadlinesNewsFromCategory(category = categoryId)
        return if (response.isSuccessful) Resource(Status.SUCCESS, response.body()?.articles, null)
        else Resource(
            Status.ERROR,
            null,
            "Terjadi kesalahan saat melakukan request data, status error ${response.code()}"
        )
    }

}