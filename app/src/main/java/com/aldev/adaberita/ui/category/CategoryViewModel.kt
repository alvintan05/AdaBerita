package com.aldev.adaberita.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.model.entity.BookmarkNewsEntity
import com.aldev.adaberita.model.response.ArticlesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    var data: LiveData<PagingData<BookmarkNewsEntity>> = MutableLiveData()

//    private val _data: MutableLiveData<PagingData<ArticlesItem>> by lazy {
//        MutableLiveData<PagingData<ArticlesItem>>()
//    }

    fun getData(categoryId: String) = viewModelScope.launch {
        val result = newsRepository.getHeadlineNewsFromCategory(categoryId).cachedIn(viewModelScope)
        data = result
    }
}