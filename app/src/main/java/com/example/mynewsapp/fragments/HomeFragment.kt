package com.example.mynewsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mynewsapp.R
import com.example.mynewsapp.adapter.ViewPager2Adapter
import com.example.mynewsapp.databinding.FragmentHomeBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewPager2Adapter: ViewPager2Adapter
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewPager2Adapter = ViewPager2Adapter(requireActivity())
        binding.viewPager2.adapter = viewPager2Adapter
        binding.viewPager2.isUserInputEnabled = false // view pager ni scroll qilmaydi

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeNews -> {
                    binding.viewPager2.setCurrentItem(0,false)
                    return@setOnItemSelectedListener true
                }
                R.id.homeFavorite -> {
                    binding.viewPager2.setCurrentItem(1,false)
                    return@setOnItemSelectedListener true
                }
                R.id.homeProfile -> {
                    binding.viewPager2.setCurrentItem(2,false)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }


        return binding.root
    }

}