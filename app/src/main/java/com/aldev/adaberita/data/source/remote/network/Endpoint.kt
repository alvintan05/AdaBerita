package com.aldev.adaberita.data.source.remote.network

import com.aldev.adaberita.data.source.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

    @GET("top-headlines")
    suspend fun getHeadlinesNews(
        @Query("country") countryId: String = "id",
        @Query("apiKey") apiKey: String = RetrofitServer.API_KEY
    ): Response<NewsResponse>

}