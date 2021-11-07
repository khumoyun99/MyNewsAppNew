package com.example.mynewsapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynewsapp.database.AppDatabase
import com.example.mynewsapp.utils.NetworkHelper

class NewsViewModelFactory(
    private val networkHelper: NetworkHelper,
    private val application: Application,
    private val appDatabase: AppDatabase
):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(NewsViewModel::class.java)->{
                NewsViewModel(networkHelper,application,appDatabase) as T
            }
            else ->throw Exception("Error")
        }
    }
}