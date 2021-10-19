package com.aldev.adaberita.ui.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebviewViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val bookmarkStatus: LiveData<Boolean> get() = _bookmarkStatus

    private val _bookmarkStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getBookmarkStatus(title: String) {
        viewModelScope.launch {
            val result = repository.checkIsNewsBookmarked(title)
            _bookmarkStatus.value = result
        }
    }

    fun addBookmark(articlesItem: ArticlesItem) {
        val entity = mapperItem(articlesItem)
        viewModelScope.launch {
            repository.addBookmark(entity)
        }
    }

    fun deleteBookmark(articlesItem: ArticlesItem) {
        val entity = mapperItem(articlesItem)
        viewModelScope.launch {
            repository.removeBookmark(entity)
        }
    }

    private fun mapperItem(item: ArticlesItem): BookmarkNewsEntity =
        BookmarkNewsEntity(
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

}