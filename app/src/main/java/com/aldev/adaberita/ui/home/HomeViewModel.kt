package com.aldev.adaberita.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.model.response.ArticlesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    lateinit var data: LiveData<PagingData<ArticlesItem>>

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {
        val result = newsRepository.getHeadlineNews().cachedIn(viewModelScope)
        data = result
    }

}