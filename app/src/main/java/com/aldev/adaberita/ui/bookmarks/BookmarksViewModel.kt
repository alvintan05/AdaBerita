package com.aldev.adaberita.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.data.source.local.entity.BookmarkNewsEntity
import com.aldev.adaberita.utils.Resource
import com.aldev.adaberita.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    val data: LiveData<Resource<List<BookmarkNewsEntity>>> get() = mData

    private val mData = MutableLiveData<Resource<List<BookmarkNewsEntity>>>().apply {
        value = Resource(null, null, null)
    }

    init {
        getData()
    }

    fun getData() = viewModelScope.launch {
        showLoading()
        val result = newsRepository.getBookmarkList()
        mData.value = mData.value?.copy(result.status, result.data, result.message)
    }

    private fun showLoading() {
        mData.value = mData.value?.copy(Status.LOADING, null, null)
    }

}