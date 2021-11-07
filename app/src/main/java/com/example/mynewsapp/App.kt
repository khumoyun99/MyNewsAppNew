package com.example.mynewsapp

import android.app.Application
import com.example.mynewsapp.database.AppDatabase

class App:Application() {

    companion object{
      lateinit var appDatabase: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase.getInstance(this)
    }
}