package com.example.mynewsapp.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewsapp.database.AppDatabase
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.repository.NewsRepository
import com.example.mynewsapp.retrofit.ApiClient
import com.example.mynewsapp.utils.NetworkHelper
import com.example.mynewsapp.utils.NewsResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val networkHelper: NetworkHelper,
    private val application: Application,
    private val appDatabase:AppDatabase = AppDatabase.getInstance(application)
):ViewModel() {

    private val liveData = MutableLiveData<NewsResource>()
    private val liveData1 = MutableLiveData<NewsResource>()
    private val liveDataSearch = MutableLiveData<NewsResource>()
    private val liveDataSortBy = MutableLiveData<NewsResource>()
    private val newsRepository = NewsRepository(ApiClient.apiService)
    internal val liveDataArticle:LiveData<List<Article>> = appDatabase.articleDao().getAllArticle()
    private var articleDao = AppDatabase.getInstance(application).articleDao()




    init {
        getAllNews1("healthy")
    }

    fun addArticle(article: Article){
        viewModelScope.launch(Dispatchers.IO){
            articleDao.addArticle(article)
//            liveDataArticle.postValue(article)
        }
    }
//
//    fun getAllArticle():LiveData<List<Article>>{
//        viewModelScope.launch {
//            val allArticle = articleDao.getAllArticle()
//        }
//
//        return liveDataArticle
//    }

    fun getAllNews(type:String):LiveData<NewsResource>{
        if(networkHelper.isNetworkConnected()){
            viewModelScope.launch{
                liveData.postValue(NewsResource.LOADING)
                newsRepository.getResultNews(type)
                    .catch {
                        liveData.postValue(NewsResource.ERROR(it.message.toString()))
                    }
                    .collect{
                        if(it.isSuccessful){
                            liveData.postValue(NewsResource.SUCCESS(it.body()!!))
                        }
                    }
            }

        }else{
            liveData.postValue(NewsResource.ERROR("Not internet connection!"))
        }
        return liveData
    }
    fun getAllNewsSortBy(type:String,sortBy:String):LiveData<NewsResource>{
        if(networkHelper.isNetworkConnected()){
            viewModelScope.launch{
                liveDataSortBy.postValue(NewsResource.LOADING)
                newsRepository.getResultNewsSortBy(type,sortBy)
                    .catch {
                        liveDataSortBy.postValue(NewsResource.ERROR(it.message.toString()))
                    }
                    .collect{
                        if(it.isSuccessful){
                            liveDataSortBy.postValue(NewsResource.SUCCESS(it.body()!!))
                        }
                    }
            }

        }else{
            liveDataSortBy.postValue(NewsResource.ERROR("Not internet connection!"))
        }
        return liveDataSortBy
    }
    fun getAllNews1(type:String):LiveData<NewsResource>{
        if(networkHelper.isNetworkConnected()){
            viewModelScope.launch{
                liveData1.postValue(NewsResource.LOADING)
                newsRepository.getResultNews(type)
                    .catch {
                        liveData1.postValue(NewsResource.ERROR(it.message.toString()))
                    }
                    .collect{
                        if(it.isSuccessful){
                            liveData1.postValue(NewsResource.SUCCESS(it.body()!!))
                        }
                    }
            }

        }else{
            liveData1.postValue(NewsResource.ERROR("Not internet connection!"))
        }
        return liveData1
    }

    fun getAllNewsSearch(type:String):LiveData<NewsResource>{
        if(networkHelper.isNetworkConnected()){
            viewModelScope.launch{
                liveDataSearch.postValue(NewsResource.LOADING)
                newsRepository.getResultNews(type)
                    .catch {
                        liveDataSearch.postValue(NewsResource.ERROR(it.message.toString()))
                    }
                    .collect{
                        if(it.isSuccessful){
                            liveDataSearch.postValue(NewsResource.SUCCESS(it.body()!!))
                        }
                    }
            }

        }else{
            liveDataSearch.postValue(NewsResource.ERROR("Not internet connection!"))
        }
        return liveDataSearch
    }

}