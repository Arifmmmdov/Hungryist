package com.example.hungryist.ui.activity.detailedinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.adapter.DetailedInfoViewPagerAdapter
import com.example.hungryist.databinding.ActivityDetailedInfoBinding
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.ui.fragment.LoginOrRegisterFragment
import com.example.hungryist.ui.fragment.details.DetailsFragment
import com.example.hungryist.utils.Constant.PLACE_ID
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailedInfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailedInfoBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: DetailedInfoViewModel

    val id by lazy {
        intent.getStringExtra(PLACE_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getDetailedInfo(id!!)
        setUpViewPager()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.savedSticker.setOnClickListener {
            viewModel.savedInfoTrigger()
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setObservers() {
        viewModel.detailedInfo.observe(this) {
            setUpViews(it)
        }
    }

    private fun setUpViews(info: DetailedInfoModel) {
        Glide.with(this).load(info.imageUrl).into(binding.mainImage)
        binding.savedSticker.setImageResource(if (info.saved) R.drawable.ic_saved_sticker else R.drawable.ic_unsaved_sticker)
        binding.name.text = info.name
        binding.rating.text = getString(R.string.rating_info, info.rating.toString())
        binding.reviews.text = getString(R.string.reviews,info.reviews)
    }

    private fun setUpViewPager() {
        val pagerAdapter = DetailedInfoViewPagerAdapter(getFragmentList(), this)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagerAdapter.getPageTitle(position)
        }.attach()
    }

    private fun getFragmentList(): List<Pair<Fragment, String>> {
        return listOf(
            Pair(DetailsFragment(), getString(R.string.details)),
            Pair(LoginOrRegisterFragment(), getString(R.string.menu))
        )
    }

    companion object {
        fun intentFor(context: Context, id: String) {
            val intent = Intent(context, DetailedInfoActivity::class.java)
            intent.putExtra(PLACE_ID, id)
            context.startActivity(intent)
        }
    }
}