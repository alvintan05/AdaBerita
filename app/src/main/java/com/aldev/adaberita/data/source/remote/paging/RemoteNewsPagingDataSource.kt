package com.aldev.adaberita.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aldev.adaberita.data.source.remote.network.Endpoint
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import com.aldev.adaberita.model.response.NewsResponse
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RemoteNewsPagingDataSource @Inject constructor(
    private val endpoint: Endpoint,
    private val category: String? = null
) :
    PagingSource<Int, BookmarkNewsEntity>() {

    private val PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, BookmarkNewsEntity>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookmarkNewsEntity> {
        val position = params.key ?: PAGE_INDEX
        var response: Response<NewsResponse>
        var data: List<ArticlesItem>
        var convertData = arrayListOf<BookmarkNewsEntity>()

        return try {
            if (category != null) {
                response =
                    endpoint.getHeadlinesNewsFromCategory(category = category, page = position)
                data = response.body()?.articles ?: emptyList()
            } else {
                response = endpoint.getHeadlinesNews(page = position)
                data = response.body()?.articles ?: emptyList()
            }

            for (item in data) {
                val entity = BookmarkNewsEntity(
                    item.title,
                    item.publishedAt,
                    item.author,
                    item.urlToImage,
                    item.description,
                    item.source?.name,
                    item.source?.id,
                    item.url,
                    item.content
                )
                convertData.add(entity)
            }

            LoadResult.Page(
                data = convertData,
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