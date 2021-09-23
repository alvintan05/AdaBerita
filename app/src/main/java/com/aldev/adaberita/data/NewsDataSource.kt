package com.aldev.adaberita.data

import com.aldev.adaberita.data.source.remote.response.ArticlesItem

interface NewsDataSource {
    suspend fun getHeadlineNews(): List<ArticlesItem>?
    suspend fun getHeadlineNewsFromCategory(categoryId: String): List<ArticlesItem>?
}