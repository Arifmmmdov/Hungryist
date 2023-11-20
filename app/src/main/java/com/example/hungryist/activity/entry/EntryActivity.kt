package com.example.hungryist.activity.entry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hungryist.databinding.ActivityEntryBinding

class EntryActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityEntryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}