package com.example.hungryist.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.hungryist.R
import com.example.hungryist.databinding.DialogChooseLocationBinding

class UpdateInfoDialog(
    val context: Activity,
) :
    Dialog(context) {

    private val binding by lazy {
        DialogChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setViews()
    }

    private fun setViews() {
        binding.chooseLocationInfo.text = context.getString(R.string.update_required_info)
        binding.btnFirst.text = context.getString(R.string.update_now)
        binding.btnSecond.text = context.getString(R.string.exit)
    }

    private fun setListeners() {

        binding.btnFirst.setOnClickListener {
            //TODO update now clicked
        }

        binding.btnSecond.setOnClickListener {
            context.finish()
        }

    }
}