package com.example.hungryist.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityEditProfileBinding
import com.example.hungryist.ui.fragment.profile.ProfileViewModel
import com.example.hungryist.utils.DynamicStarFillUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: ProfileViewModel

    private val starFillUtil by lazy {
        DynamicStarFillUtil(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setViews()

    }

    private fun setViews() {
        starFillUtil.fillStars(3.5, true)
    }

    private fun setListeners() {

    }

    companion object {
        fun intentFor(context: Context) {
            context.startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }
}