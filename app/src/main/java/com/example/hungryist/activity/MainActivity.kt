package com.example.hungryist.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hungryist.constants.Constants.IS_GUEST
import com.example.hungryist.databinding.ActivityEntryBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEntryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        fun intentFor(context: Context, isGuest: Boolean) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(IS_GUEST, isGuest)
            context.startActivity(intent)
        }
    }
}