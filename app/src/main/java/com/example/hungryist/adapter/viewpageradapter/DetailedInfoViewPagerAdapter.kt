package com.example.hungryist.adapter.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailedInfoViewPagerAdapter(
    private val fragmentList: List<Pair<Fragment, String>>,
    fragmentActivity: FragmentActivity,
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position].first

    fun getPageTitle(position: Int): CharSequence = fragmentList[position].second

}
