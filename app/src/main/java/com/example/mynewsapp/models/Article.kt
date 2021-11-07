package com.example.mynewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class  Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
//    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
):Serializable