package com.example.mynewsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mynewsapp.fragments.FavoriteNewsFragment
import com.example.mynewsapp.fragments.HomeNewsFragment
import com.example.mynewsapp.fragments.ProfileFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> HomeNewsFragment()
            1->FavoriteNewsFragment()
            else -> ProfileFragment()
        }
    }
}