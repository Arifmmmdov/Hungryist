package com.example.hungryist.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.fragment.LoginOrRegisterFragment
import com.example.hungryist.api.APIRequestInterface
import com.example.hungryist.api.Currencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var requestInterface: APIRequestInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFragment()
    }

    private fun showFragment() {
        lifecycleScope.launch(Dispatchers.IO) {
            val body = requestInterface.getCurrencies("X-RapidAPI-Key", "X-RapidAPI-Host")

            Log.d("MyTagHere", "showFragment: ")
            Log.d("MyTagHere", "showFragment: ${Currencies().response.request.body.toString()}")
        }
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