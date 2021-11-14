package com.example.mynewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mynewsapp.databinding.ActivitySecondBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds

class SecondActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySecondBinding
    private val TAG = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

////        MobileAds.initialize(this){}
//        val adRequest = AdRequest.Builder().build()
//        binding.adView.loadAd(adRequest)
//
//        binding.adView.adListener = object : AdListener(){
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//                Log.d(TAG, "onAdLoaded: ")
//            }
//
//            override fun onAdFailedToLoad(p0: LoadAdError) {
//                super.onAdFailedToLoad(p0)
//                Log.d(TAG, "onAdFailedToLoad: ")
//            }
//
//            override fun onAdOpened() {
//                super.onAdOpened()
//                Log.d(TAG, "onAdOpened: ")
//            }
//
//            override fun onAdClicked() {
//                super.onAdClicked()
//                Log.d(TAG, "onAdClicked: ")
//            }
//            override fun onAdClosed() {
//                super.onAdClosed()
//                Log.d(TAG, "onAdClosed: ")
//            }
//
//        }
    }
}