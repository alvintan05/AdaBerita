package com.aldev.adaberita.data

import androidx.lifecycle.LiveData
import com.aldev.adaberita.data.source.remote.response.ArticlesItem
import com.aldev.adaberita.data.source.remote.response.NewsResponse

interface NewsDataSource {

    suspend fun getHeadlineNews(): List<ArticlesItem>?

}