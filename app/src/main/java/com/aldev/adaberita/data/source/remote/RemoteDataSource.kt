package com.aldev.adaberita.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.aldev.adaberita.data.source.remote.network.Endpoint
import com.aldev.adaberita.data.source.remote.paging.RemoteNewsPagingDataSource
import com.aldev.adaberita.model.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val endpoint: Endpoint
) {

    fun getHeadlineNews() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RemoteNewsPagingDataSource(endpoint) }
    ).liveData

    fun getHeadlineNewsFromCategory(categoryId: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RemoteNewsPagingDataSource(endpoint, categoryId) }
    ).liveData
}