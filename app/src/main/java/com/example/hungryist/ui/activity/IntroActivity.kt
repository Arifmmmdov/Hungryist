package com.example.hungryist.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.ui.fragment.LoginOrRegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFragment()
    }

    private fun showFragment() {

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, LoginOrRegisterFragment())
            .commit()
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, IntroActivity::class.java)
            context.startActivity(intent)
        }
    }
}