package com.example.hungryist.ui.fragment.interior

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentInteriorBinding
import com.example.hungryist.databinding.FragmentMenuBinding

class InteriorFragment : Fragment() {

    val binding by lazy {
        FragmentInteriorBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }
}