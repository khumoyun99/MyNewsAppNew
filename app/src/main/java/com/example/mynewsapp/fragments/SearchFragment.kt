package com.example.mynewsapp.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.AllNewsRvAdapter
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
        val bundle = Bundle(arguments)
        val search = bundle.getString("search")


        binding.apply {
            binding.searchEdit.setText(search.toString())
            filterLl.setOnClickListener {
                filterImg.setImageResource(R.drawable.filter_sel)
                filterLl.setBackgroundResource(R.drawable.selected_tab)
                filer.setTextColor(Color.WHITE)

                val bottomSheetDialog = BottomSheetDialog(requireContext())
                val customBottomSheetDialog = CustomBottomSheetBinding.inflate(layoutInflater)

                bottomSheetDialog.setContentView(customBottomSheetDialog.root)

                customBottomSheetDialog.saveCv.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }

                bottomSheetDialog.setCancelable(false)
//                bottomSheetDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                bottomSheetDialog.show()
            }
        }

        newsViewModel = ViewModelProvider(requireActivity(),NewsViewModelFactory(networkHelper))[NewsViewModel::class.java]
        newsViewModel.getAllNews(search.toString()).observe(requireActivity(),{
            when(it){
                is NewsResource.LOADING -> {
                    Log.d(TAG, "onCreateView: Loading")
                }
                is NewsResource.ERROR -> {
                    Log.d(TAG, "onCreateView: ${it.message}")
                }
                is NewsResource.SUCCESS ->{
                    Log.d(TAG, "onCreateView: ${it.allNews}")
                    articleList = ArrayList(it.allNews.articles)
                    if(articleList.size==0){
                        articleList = ArrayList(emptyList())
                    }
                    allRvNewsRvAdapter = AllNewsRvAdapter(articleList,object :AllNewsRvAdapter.OnItemClickListener{
                        override fun onItemClick(article: Article) {
                            val bundle1 = Bundle()
                            bundle1.putSerializable("article",article)
                            findNavController().navigate(R.id.itemLatestNewsFragment,bundle1)
                        }
                    })
                    binding.allRvNew.adapter = allRvNewsRvAdapter
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
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}