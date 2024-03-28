package com.example.hungryist.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {

    private val binding: ActivityAboutUsBinding by lazy {
        ActivityAboutUsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()

        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = getString(R.string.about_us)
    }

    private fun setListeners() {
        binding.fab.setOnClickListener {
            //TODO add phone call or email add click action to the contact fab
        }
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, AboutUsActivity::class.java)
            context.startActivity(intent)
        }
    }
}