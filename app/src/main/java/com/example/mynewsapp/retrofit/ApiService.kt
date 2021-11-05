package com.example.mynewsapp.retrofit

import com.example.mynewsapp.models.AllNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?apiKey=ddd8a92aca7b497ab2015e10b53a16c2")
    suspend fun getNews(
        @Query("q")q:String
    ):Response<AllNews>
}