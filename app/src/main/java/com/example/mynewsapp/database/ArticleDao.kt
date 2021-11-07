package com.example.mynewsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynewsapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: Article)

    @Query("select * from article")
    fun getAllArticle():LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)


}