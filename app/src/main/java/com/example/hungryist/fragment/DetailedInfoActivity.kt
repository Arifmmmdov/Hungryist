package com.example.hungryist.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.hungryist.adapter.DetailedInfoViewPagerAdapter
import com.example.hungryist.databinding.ActivityDetailedInfoBinding

class DetailedInfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailedInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(binding.root)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter =
            DetailedInfoViewPagerAdapter(getFragmentList(), this)
    }

    private fun getFragmentList(): List<Fragment> {
        return listOf()
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, DetailedInfoActivity::class.java)
            context.startActivity(intent)
        }
    }
}