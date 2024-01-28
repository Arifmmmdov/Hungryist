package com.example.hungryist.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityEditProfileBinding
import com.example.hungryist.utils.DynamicStarFillUtil

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

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