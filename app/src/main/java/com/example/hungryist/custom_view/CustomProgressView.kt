package com.example.hungryist.custom_view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.translation.ViewTranslationRequest
import com.example.hungryist.databinding.ProgressBarBinding
import com.google.android.material.navigation.NavigationView
import java.util.function.Consumer


class CustomProgressView(context: Context) : Dialog(context) {

    private val binding by lazy {
        ProgressBarBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}