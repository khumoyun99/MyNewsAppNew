package com.example.mynewsapp.utils

import com.example.mynewsapp.models.AllNews

sealed class NewsResource {

    object LOADING:NewsResource()

    class SUCCESS(val allNews: AllNews ):NewsResource()

    class ERROR(val message:String):NewsResource()
}