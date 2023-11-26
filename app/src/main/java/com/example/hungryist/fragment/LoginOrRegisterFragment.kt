package com.example.hungryist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.activity.MainActivity
import com.example.hungryist.databinding.FragmentLoginOrRegisterBinding

class LoginOrRegisterFragment : Fragment() {

    private val binding by lazy {
        FragmentLoginOrRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.continueAsGuest.setOnClickListener {
            moveToMainView()
        }
    }

    private fun moveToMainView() {
        MainActivity.intentFor(requireContext(), true)
    }

}