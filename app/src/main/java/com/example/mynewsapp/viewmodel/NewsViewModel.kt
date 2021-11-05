package com.example.mynewsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewsapp.repository.NewsRepository
import com.example.mynewsapp.retrofit.ApiClient
import com.example.mynewsapp.utils.NetworkHelper
import com.example.mynewsapp.utils.NewsResource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(private val networkHelper: NetworkHelper):ViewModel() {

    private val liveData = MutableLiveData<NewsResource>()
    private val liveData1 = MutableLiveData<NewsResource>()
    private val newsRepository = NewsRepository(ApiClient.apiService)

    init {
        getAllNews1("healthy")
    }

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

}