package com.aldev.adaberita.data.source.remote

import com.aldev.adaberita.data.source.remote.network.Endpoint
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val endpoint: Endpoint
) {

    suspend fun getHeadlineNews(): Resource<List<ArticlesItem>> =
        try {
            val response = endpoint.getHeadlinesNews()
            val result = response.body()?.articles
            if (response.isSuccessful) {
                if (result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Empty()
                }
            } else {
                Resource.Error("Error Occured")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.localizedMessage}")
        }

    suspend fun getHeadlineNewsFromCategory(categoryId: String): Resource<List<ArticlesItem>> =
        try {
            val response = endpoint.getHeadlinesNewsFromCategory(category = categoryId)
            val result = response.body()?.articles
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("Error Occured")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.localizedMessage}")
        }
}