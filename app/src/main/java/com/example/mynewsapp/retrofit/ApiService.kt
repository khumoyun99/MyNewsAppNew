package com.example.mynewsapp.retrofit

import com.example.mynewsapp.models.AllNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?apiKey=38cc2d27abe94b76a273d0a6acb5a0f7")
    suspend fun getNews(
        @Query("q")q:String
    ):Response<AllNews>

    @GET("?apiKey=38cc2d27abe94b76a273d0a6acb5a0f7")
    suspend fun getNewsSortBy(
        @Query("q")q:String,
        @Query("sortBy")sortBy:String
    ):Response<AllNews>
}