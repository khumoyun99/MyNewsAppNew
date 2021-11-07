package com.example.mynewsapp.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynewsapp.App
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.AllNewsRvAdapter
import com.example.mynewsapp.database.AppDatabase
import com.example.mynewsapp.databinding.CustomBottomSheetBinding
import com.example.mynewsapp.databinding.FragmentSearchBinding
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.utils.NetworkHelper
import com.example.mynewsapp.utils.NewsResource
import com.example.mynewsapp.viewmodel.NewsViewModel
import com.example.mynewsapp.viewmodel.NewsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {


    private lateinit var binding:FragmentSearchBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var allRvNewsRvAdapter: AllNewsRvAdapter
    private lateinit var articleList:ArrayList<Article>
    private lateinit var appDatabase: AppDatabase
    private lateinit var application: App
    private var tabList = arrayListOf("healthy","technology","finance","arts","sports","medicine")
    private var isRecommended = false
    private var isLatest = false
    private var isMostView = false
    private var isChannel = false
    private var isFollowing = false
    private var isReset=false


    private val TAG = "SearchFragment"
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        networkHelper = NetworkHelper(requireContext())
        appDatabase = AppDatabase.getInstance(requireContext())
        application = App()
        val bundle = Bundle(arguments)
        var search = bundle.getString("search")
        newsViewModel = ViewModelProvider(requireActivity(),NewsViewModelFactory(
            networkHelper,
            application,
            appDatabase
        ))[NewsViewModel::class.java]



        binding.apply {
            searchImg.setOnClickListener{
                search = binding.searchEdit.text.toString()
                newsViewModel.getAllNewsSearch(search.toString()).observe(requireActivity(),{
                    when(it){
                        is NewsResource.LOADING -> {
                            Log.d(TAG, "onCreateView: Loading")
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is NewsResource.ERROR -> {
                            Log.d(TAG, "onCreateView: ${it.message}")
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.errorMessageTv.text = "Not internet connection"
                            binding.errorMessageTv.visibility = View.VISIBLE
                        }
                        is NewsResource.SUCCESS ->{
                            Log.d(TAG, "onCreateView: ${it.allNews}")
                            binding.progressBar.visibility = View.INVISIBLE
                            articleList = ArrayList(it.allNews.articles)
                            if(it.allNews.totalResults!=0){
                                binding.errorMessageTv.visibility = View.INVISIBLE
                                allRvNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                                    override fun onItemClick(article: Article) {
                                        val bundle1 = Bundle()
                                        bundle1.putSerializable("article",article)
                                        findNavController().navigate(R.id.itemLatestNewsFragment,bundle1)
                                    }
                                })
                                binding.allRvNew.adapter = allRvNewsRvAdapter
                            }else{
                                binding.errorMessageTv.text = "No News"
                                binding.errorMessageTv.visibility = View.VISIBLE
                            }

                        }
                    }
                })
            }

            binding.searchEdit.setText(search.toString())

            filterLl.setOnClickListener {
                filterImg.setImageResource(R.drawable.filter_sel)
                filterLl.setBackgroundResource(R.drawable.selected_tab)
                filer.setTextColor(Color.WHITE)

                val bottomSheetDialog = BottomSheetDialog(requireContext())
                val customBottomSheetDialog = CustomBottomSheetBinding.inflate(layoutInflater)
                bottomSheetDialog.setContentView(customBottomSheetDialog.root)

                customBottomSheetDialog.recommendedTv.setOnClickListener {
                    if(isRecommended){
                        it.setBackgroundResource(R.drawable.unselected_tab)
                        isRecommended=false
                    }else {
                        it.setBackgroundResource(R.drawable.selected_tab)
                        isRecommended=true
                    }
                }
                customBottomSheetDialog.latestTv.setOnClickListener {
                    if(isLatest){
                        it.setBackgroundResource(R.drawable.unselected_tab)
                        isLatest=false
                    }else {
                        it.setBackgroundResource(R.drawable.selected_tab)
                        isLatest=true
                    }
                }
                customBottomSheetDialog.mostViewedTv.setOnClickListener {
                    if(isMostView){
                        it.setBackgroundResource(R.drawable.unselected_tab)
                        isMostView=false
                    }else {
                        it.setBackgroundResource(R.drawable.selected_tab)
                        isMostView=true
                    }
                }
                customBottomSheetDialog.channelTv.setOnClickListener {
                    if(isChannel){
                        it.setBackgroundResource(R.drawable.unselected_tab)
                        isChannel=false
                    }else {
                        it.setBackgroundResource(R.drawable.selected_tab)
                        isChannel=true
                    }
                }
                customBottomSheetDialog.followingTv.setOnClickListener {
                    if(isFollowing){
                        it.setBackgroundResource(R.drawable.unselected_tab)
                        isFollowing=false
                    }else {
                        it.setBackgroundResource(R.drawable.selected_tab)
                        isFollowing=true
                    }
                }
                customBottomSheetDialog.resetLl.setOnClickListener {
                    if (isReset){
                        it.setBackgroundResource(R.drawable.unselected_tab)
                        unSelectAll(customBottomSheetDialog)
                        isReset=false
                    }else{
                        it.setBackgroundResource(R.drawable.selected_tab)
                        selectAll(customBottomSheetDialog)
                        isReset=true
                    }
                }

                customBottomSheetDialog.saveCv.setOnClickListener {
                    newsViewModel.getAllNewsSortBy(search.toString(),"popularity").observe(requireActivity(),{
                        when(it){
                            is NewsResource.LOADING -> {
                                Log.d(TAG, "onCreateView: Loading")
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is NewsResource.ERROR -> {
                                Log.d(TAG, "onCreateView: ${it.message}")
                                binding.progressBar.visibility = View.INVISIBLE
                                binding.errorMessageTv.text = "Not internet connection"
                                binding.errorMessageTv.visibility = View.VISIBLE
                            }
                            is NewsResource.SUCCESS ->{
                                Log.d(TAG, "onCreateView: ${it.allNews}")
                                binding.progressBar.visibility = View.INVISIBLE
                                articleList = ArrayList(it.allNews.articles)
                                if(it.allNews.totalResults!=0){
                                    binding.errorMessageTv.visibility = View.INVISIBLE
                                    allRvNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                                        override fun onItemClick(article: Article) {
                                            val bundle1 = Bundle()
                                            bundle1.putSerializable("article",article)
                                            findNavController().navigate(R.id.itemLatestNewsFragment,bundle1)
                                        }
                                    })
                                    binding.allRvNew.adapter = allRvNewsRvAdapter
                                }else{
                                    binding.errorMessageTv.text = "No News"
                                    binding.errorMessageTv.visibility = View.VISIBLE
                                }

                            }
                        }
                    })
                    bottomSheetDialog.dismiss()

                }

                bottomSheetDialog.setCancelable(false)
//                bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                bottomSheetDialog.show()
            }
        }


        newsViewModel.getAllNewsSearch(search.toString()).observe(requireActivity(),{
            when(it){
                is NewsResource.LOADING -> {
                    Log.d(TAG, "onCreateView: Loading")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NewsResource.ERROR -> {
                    Log.d(TAG, "onCreateView: ${it.message}")
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.errorMessageTv.text = "Not internet connection"
                    binding.errorMessageTv.visibility = View.VISIBLE
                }
                is NewsResource.SUCCESS ->{
                    Log.d(TAG, "onCreateView: ${it.allNews}")
                    binding.progressBar.visibility = View.INVISIBLE
                    articleList = ArrayList(it.allNews.articles)
                    if(it.allNews.totalResults!=0){
                        binding.errorMessageTv.visibility = View.INVISIBLE
                        allRvNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                            override fun onItemClick(article: Article) {
                                val bundle1 = Bundle()
                                bundle1.putSerializable("article",article)
                                findNavController().navigate(R.id.itemLatestNewsFragment,bundle1)
                            }
                        })
                        binding.allRvNew.adapter = allRvNewsRvAdapter
                    }else{
                        binding.errorMessageTv.text = "No News"
                        binding.errorMessageTv.visibility = View.VISIBLE
                    }

                }
            }
        })

        binding.tabLayoutNews.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            @SuppressLint("ResourceAsColor")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.apply {
                    filterImg.setImageResource(R.drawable.filter_un)
                    filterLl.setBackgroundResource(R.drawable.unselected_tab)
                    filer.setTextColor(Color.BLACK)
                }
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
                            allRvNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                                override fun onItemClick(article: Article) {
                                    val bundleSearch = Bundle()
                                    bundleSearch.putSerializable("article",article)
                                    findNavController().navigate(R.id.itemLatestNewsFragment,bundleSearch)
                                }
                            })
                            binding.allRvNew.adapter = allRvNewsRvAdapter

                        }
                    }
                })
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


        return binding.root
    }


    private fun selectAll(customBottomSheetBinding: CustomBottomSheetBinding){
        customBottomSheetBinding.recommendedTv.setBackgroundResource(R.drawable.selected_tab)
        customBottomSheetBinding.latestTv.setBackgroundResource(R.drawable.selected_tab)
        customBottomSheetBinding.mostViewedTv.setBackgroundResource(R.drawable.selected_tab)
        customBottomSheetBinding.channelTv.setBackgroundResource(R.drawable.selected_tab)
        customBottomSheetBinding.followingTv.setBackgroundResource(R.drawable.selected_tab)
        isRecommended=true
        isLatest=true
        isMostView=true
        isChannel=true
        isFollowing=true

    }
    private fun unSelectAll(customBottomSheetBinding: CustomBottomSheetBinding){
        customBottomSheetBinding.recommendedTv.setBackgroundResource(R.drawable.unselected_tab)
        customBottomSheetBinding.latestTv.setBackgroundResource(R.drawable.unselected_tab)
        customBottomSheetBinding.mostViewedTv.setBackgroundResource(R.drawable.unselected_tab)
        customBottomSheetBinding.channelTv.setBackgroundResource(R.drawable.unselected_tab)
        customBottomSheetBinding.followingTv.setBackgroundResource(R.drawable.unselected_tab)
        isRecommended=false
        isLatest=false
        isMostView=false
        isChannel=false
        isFollowing=false

    }


}