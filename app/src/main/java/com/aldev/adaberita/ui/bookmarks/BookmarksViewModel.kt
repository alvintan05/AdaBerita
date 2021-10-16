package com.aldev.adaberita.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    val data: LiveData<Resource<List<BookmarkNewsEntity>>> get() = _data

    private val _data: MutableLiveData<Resource<List<BookmarkNewsEntity>>> by lazy {
        MutableLiveData<Resource<List<BookmarkNewsEntity>>>().also {
            getData()
        }
    }

    fun getData() = viewModelScope.launch {
        _data.value = Resource.Loading()
        val result = newsRepository.getBookmarkList()
        _data.value = result
    }

}