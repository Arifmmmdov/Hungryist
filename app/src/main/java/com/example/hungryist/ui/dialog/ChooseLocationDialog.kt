package com.example.hungryist.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.hungryist.databinding.DialogChooseLocationBinding

class ChooseLocationDialog(
    context: Context,
    val onChooseFromMapClicked: () -> Unit,
    val onCurrentLocationClicked: () -> Unit,
) :
    Dialog(context) {

    private val binding by lazy {
        DialogChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.btnChooseFromMap.setOnClickListener {
            onChooseFromMapClicked()
            dismiss()
        }

        binding.btnCurrentLocation.setOnClickListener {
            onCurrentLocationClicked()
            dismiss()
        }
    }
}