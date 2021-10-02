package com.aldev.adaberita.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.adaberita.data.NewsRepository
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.utils.Resource
import com.aldev.adaberita.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {
    val data: LiveData<Resource<List<ArticlesItem>>> get() = mData

    private val mData = MutableLiveData<Resource<List<ArticlesItem>>>().apply {
        value = Resource(null, null, null)
    }

    fun getData(categoryId: String) = viewModelScope.launch {
        showLoading()
        val result = newsRepository.getHeadlineNewsFromCategory(categoryId)
        mData.value = mData.value?.copy(result.status, result.data, result.message)
    }

    private fun showLoading(){
        mData.value = mData.value?.copy(Status.LOADING, null, null)
    }
}