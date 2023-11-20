package com.example.hungryist.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private val binding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        runCountDownTimer()
    }

    private fun runCountDownTimer() {
        object : CountDownTimer(3000,1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                val fragmentManager: FragmentManager = parentFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,LoginOrRegisterFragment::class.java,null)
                    .commit()
            }
        }.start()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}