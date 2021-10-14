package com.aldev.adaberita.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    val data: LiveData<Resource<List<ArticlesItem>>> get() = _data

    private val _data: MutableLiveData<Resource<List<ArticlesItem>>> by lazy {
        MutableLiveData<Resource<List<ArticlesItem>>>().also {
            getData()
        }
    }

    fun getData() = viewModelScope.launch {
        _data.value = Resource.Loading()
        val result = newsRepository.getHeadlineNews()
        _data.value = result
    }

}