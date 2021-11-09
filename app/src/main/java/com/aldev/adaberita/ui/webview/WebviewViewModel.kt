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

    lateinit var bookmarkStatus: LiveData<Boolean>

    fun getBookmarkStatus(title: String) {
        viewModelScope.launch {
            val result = repository.checkIsNewsBookmarked(title)
            bookmarkStatus = result
        }
    }

    fun addBookmark(item: BookmarkNewsEntity) {
        viewModelScope.launch {
            repository.addBookmark(item)
        }
    }

    fun deleteBookmark(item: BookmarkNewsEntity) {
        viewModelScope.launch {
            repository.removeBookmark(item)
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