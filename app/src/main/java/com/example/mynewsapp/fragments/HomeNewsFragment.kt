package com.example.mynewsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.AllNewsRvAdapter
import com.example.mynewsapp.adapter.LatestNewsRvAdapter
import com.example.mynewsapp.databinding.FragmentHomeNewsBinding
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.utils.NetworkHelper
import com.example.mynewsapp.utils.NewsResource
import com.example.mynewsapp.viewmodel.NewsViewModel
import com.example.mynewsapp.viewmodel.NewsViewModelFactory
import com.google.android.material.tabs.TabLayout

class HomeNewsFragment : Fragment() {

    private lateinit var binding:FragmentHomeNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var latestNewsRvAdapter: LatestNewsRvAdapter
    private lateinit var articleList: ArrayList<Article>
    private lateinit var allNewsRvAdapter: AllNewsRvAdapter
    private var tabList = arrayListOf("healthy","technology","finance","arts","sports","medicine")
    private val TAG = "HomeNewsFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeNewsBinding.inflate(inflater,container,false)
        articleList = ArrayList()
        networkHelper = NetworkHelper(requireContext())
        newsViewModel = ViewModelProvider(requireActivity(),NewsViewModelFactory(networkHelper))[NewsViewModel::class.java]
        newsViewModel.getAllNews("favorite").observe(requireActivity(),{
            when(it){
                is NewsResource.LOADING ->{
                    Log.d(TAG, "onCreateView: Loading")
                }
                is NewsResource.ERROR ->{
                    Log.d(TAG, "onCreateView: ${it.message}")
                }
                is NewsResource.SUCCESS ->{
                    Log.d(TAG, "onCreateView: ${it.allNews}")
                    articleList = ArrayList(it.allNews.articles)
                    latestNewsRvAdapter = LatestNewsRvAdapter(articleList,object :LatestNewsRvAdapter.OnItemClickListener{
                        override fun onItemClick(article: Article) {
                            val bundle = Bundle()
                            bundle.putSerializable("article",article)
                            findNavController().navigate(R.id.itemLatestNewsFragment,bundle)
                        }
                    })
                    binding.latestRv.adapter = latestNewsRvAdapter
                }
            }
        })

        binding.tabLayoutNews.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                newsViewModel.getAllNews1(tabList[tab?.position?:0]).observe(requireActivity(),{
                    when(it){
                        is NewsResource.LOADING ->{
                            Log.d(TAG, "onCreateView: Loading")
                        }
                        is NewsResource.ERROR ->{
                            Log.d(TAG, "onCreateView: ${it.message}")
                        }
                        is NewsResource.SUCCESS ->{
                            Log.d(TAG, "onCreateView: ${it.allNews}")
                            articleList = ArrayList(it.allNews.articles)
                            allNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                                override fun onItemClick(article: Article) {
                                    val bundle = Bundle()
                                    bundle.putSerializable("article",article)
                                    findNavController().navigate(R.id.itemLatestNewsFragment,bundle)
                                }
                            })
                            binding.allRvNew.adapter = allNewsRvAdapter

                        }
                    }
                })



            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {


            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.searchImg.setOnClickListener {
            val searchText = binding.searchEdit.text.toString()
            if(searchText.isNotEmpty()){
                val bundle = Bundle()
                bundle.putString("search",searchText)
                findNavController().navigate(R.id.searchFragment,bundle)
            }
        }



        return binding.root
    }

    override fun onResume() {
        newsViewModel.getAllNews1("healthy").observe(requireActivity(),{
            when(it){
                is NewsResource.LOADING ->{
                    Log.d(TAG, "onCreateView: Loading")
                }
                is NewsResource.ERROR ->{
                    Log.d(TAG, "onCreateView: ${it.message}")
                }
                is NewsResource.SUCCESS ->{
                    Log.d(TAG, "onCreateView: ${it.allNews}")
                    articleList = ArrayList(it.allNews.articles)
                    allNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                        override fun onItemClick(article: Article) {
                            val bundle = Bundle()
                            bundle.putSerializable("article",article)
                            findNavController().navigate(R.id.itemLatestNewsFragment,bundle)
                        }
                    })
                    binding.allRvNew.adapter = allNewsRvAdapter

                }
            }
        })

        super.onResume()
    }
}