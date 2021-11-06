package com.example.mynewsapp.repository

import com.example.mynewsapp.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class NewsRepository(private val apiService: ApiService) {

    fun getResultNews(type:String) = flow { emit(apiService.getNews(type)) }
    fun getResultNewsSortBy(type:String,sortBy:String) = flow { emit(apiService.getNewsSortBy(type,sortBy)) }
}