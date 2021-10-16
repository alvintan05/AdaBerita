package com.aldev.adaberita.data.source.remote.network

import com.aldev.adaberita.model.response.NewsResponse
import com.aldev.adaberita.utils.ApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

    @GET("top-headlines")
    suspend fun getHeadlinesNews(
        @Query("country") countryId: String = "id",
        @Query("apiKey") apiKey: String = ApiKey.API_KEY,
        @Query("page") page: Int
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getHeadlinesNewsFromCategory(
        @Query("country") countryId: String = "id",
        @Query("apiKey") apiKey: String = ApiKey.API_KEY,
        @Query("category") category: String
    ): Response<NewsResponse>

}