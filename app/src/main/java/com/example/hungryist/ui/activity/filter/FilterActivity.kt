package com.example.hungryist.ui.activity.filter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hungryist.R
import com.example.hungryist.adapter.viewpageradapter.FragmentViewPagerAdapter
import com.example.hungryist.databinding.ActivityFilterBinding
import com.example.hungryist.ui.fragment.filter.MealFilterFragment
import com.example.hungryist.ui.fragment.filter.PlaceFilterFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFilterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViews()
        setListeners()
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setViews() {
        val pagerAdapter = FragmentViewPagerAdapter(getFragmentList(), this)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagerAdapter.getPageTitle(position)
        }.attach()
    }

    private fun getFragmentList(): List<Pair<Fragment, String>> {
        return listOf(
            Pair(PlaceFilterFragment(), getString(R.string.place)),
            Pair(MealFilterFragment(), getString(R.string.meal))
        )
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, FilterActivity::class.java)
            context.startActivity(intent)
        }
    }

}