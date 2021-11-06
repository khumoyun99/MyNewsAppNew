package com.example.mynewsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentItemLatestNewsBinding
import com.example.mynewsapp.models.Article
import com.squareup.picasso.Picasso

class ItemLatestNewsFragment : Fragment() {
    private lateinit var binding:FragmentItemLatestNewsBinding
    private var islike=false


    override fun onCreateView
                (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemLatestNewsBinding.inflate(inflater,container,false)
        val bundle = Bundle(arguments)
        val article:Article = bundle.getSerializable("article") as Article
        binding.backArrowImg.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.favoriteImg.setOnClickListener{
            if(islike){
                binding.favoriteImg.setBackgroundResource(R.drawable.favorite_shape_un)
                islike=false
            }else{
                binding.favoriteImg.setBackgroundResource(R.drawable.favorite_shape)
                islike=true

            }
        }

        Picasso.get().load(article.urlToImage).into(binding.itemLatestImg)
        binding.textTv.text = article.description



        return binding.root
    }

}