package com.example.mynewsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynewsapp.models.Article


@Database(entities = [Article::class],version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun articleDao():ArticleDao

    companion object{
        @Volatile
        private var appDatabase:AppDatabase?=null

        @Synchronized
        fun getInstance(context: Context):AppDatabase{

            if(appDatabase==null){
                appDatabase = Room.databaseBuilder(context,AppDatabase::class.java,"my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}