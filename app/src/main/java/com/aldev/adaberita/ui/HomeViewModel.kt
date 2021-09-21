package com.aldev.adaberita.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import com.aldev.adaberita.utils.Status
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val data: LiveData<Resource<List<ArticlesItem>>> get() = mData

    private val mData = MutableLiveData<Resource<List<ArticlesItem>>>().apply {
        value = Resource(Status.LOADING, null, null)
    }

    init {
        getData()
    }

    fun getData() = viewModelScope.launch {
        val result = newsRepository.getHeadlineNews()
        mData.value = mData.value?.copy(Status.SUCCESS, result, null)
    }

}