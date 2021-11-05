package com.example.mynewsapp.models

data class AllNews(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)