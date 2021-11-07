package com.example.mynewsapp.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynewsapp.App
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.AllNewsRvAdapter
import com.example.mynewsapp.database.AppDatabase
import com.example.mynewsapp.databinding.FragmentFavoriteNewsBinding
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.utils.NetworkHelper
import com.example.mynewsapp.viewmodel.NewsViewModel
import com.example.mynewsapp.viewmodel.NewsViewModelFactory

class FavoriteNewsFragment : Fragment() {

    private lateinit var binding:FragmentFavoriteNewsBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var allNewsRvAdapter: AllNewsRvAdapter
    private lateinit var application:App

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFavoriteNewsBinding.inflate(inflater,container,false)
        networkHelper = NetworkHelper(requireContext())
        application = App()
        appDatabase = AppDatabase.getInstance(requireContext())
        newsViewModel = ViewModelProvider(requireActivity(),NewsViewModelFactory(
            networkHelper,
            application,
            appDatabase
        ))[NewsViewModel::class.java]

        newsViewModel.liveDataArticle.observe(requireActivity(), Observer {
            allNewsRvAdapter = AllNewsRvAdapter(ArrayList(it),object :AllNewsRvAdapter.OnItemClickListener{
                override fun onItemClick(article: Article) {
                    val bundle = Bundle()
                    bundle.putSerializable("article",article)
                    findNavController().navigate(R.id.itemLatestNewsFragment,bundle)
                }
            })

            binding.favoriteRv.adapter = allNewsRvAdapter

        })


        return binding.root
    }

}