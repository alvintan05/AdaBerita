package com.aldev.adaberita.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aldev.adaberita.data.source.remote.network.Endpoint
import com.aldev.adaberita.model.response.ArticlesItem
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RemoteNewsPagingDataSource @Inject constructor(private val endpoint: Endpoint) :
    PagingSource<Int, ArticlesItem>() {

    private val PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        val position = params.key ?: PAGE_INDEX

        return try {
            val response = endpoint.getHeadlinesNews(page = position)
            val data = response.body()?.articles ?: emptyList()
            LoadResult.Page(
                data = data,
                prevKey = if (position == PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}