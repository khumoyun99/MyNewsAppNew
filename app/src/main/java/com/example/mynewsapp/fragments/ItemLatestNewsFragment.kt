package com.example.mynewsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynewsapp.App
import com.example.mynewsapp.R
import com.example.mynewsapp.database.AppDatabase
import com.example.mynewsapp.databinding.FragmentItemLatestNewsBinding
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.utils.NetworkHelper
import com.example.mynewsapp.viewmodel.NewsViewModel
import com.example.mynewsapp.viewmodel.NewsViewModelFactory
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.squareup.picasso.Picasso

class ItemLatestNewsFragment : Fragment() {
    private lateinit var binding:FragmentItemLatestNewsBinding
    private lateinit var appDatabase: AppDatabase
    private var islike=false
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var application:App
    private val TAG = "ItemLatestNewsFragment"


    override fun onCreateView
                (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemLatestNewsBinding.inflate(inflater,container,false)
        networkHelper = NetworkHelper(requireContext())
        application = App()
        appDatabase = AppDatabase.getInstance(requireContext())


        val bundle = Bundle(arguments)
        val article:Article = bundle.getSerializable("article") as Article
        newsViewModel = ViewModelProvider(
            requireActivity(),
            NewsViewModelFactory(
                networkHelper,
                application,
                appDatabase
            )
        )[NewsViewModel::class.java]

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = object :AdListener(){
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(TAG, "onAdLoaded: ")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Log.d(TAG, "onAdFailedToLoad: ")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(TAG, "onAdOpened: ")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(TAG, "onAdClicked: ")
            }
            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(TAG, "onAdClosed: ")
            }

        }



        newsViewModel.liveDataArticle.observe(requireActivity(),{
            it.forEach { article1 ->
                if(article1.url==article.url){
                    islike=true
                }
            }
        })
        if(islike){
            binding.favoriteImg.setBackgroundResource(R.drawable.favorite_shape)
        }else{
            binding.favoriteImg.setBackgroundResource(R.drawable.favorite_shape_un)
        }


        binding.backArrowImg.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.favoriteImg.setOnClickListener{
            if(islike){
                binding.favoriteImg.setBackgroundResource(R.drawable.favorite_shape_un)
                islike=false
                newsViewModel.deleteArticle(article)
            }else{
                binding.favoriteImg.setBackgroundResource(R.drawable.favorite_shape)
                islike=true
                newsViewModel.addArticle(article)
            }
        }

        Picasso.get().load(article.urlToImage).into(binding.itemLatestImg)
        binding.textTv.text = article.description



        return binding.root
    }


}