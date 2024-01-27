package com.example.hungryist.ui.fragment.login_or_register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.databinding.FragmentLoginOrRegisterBinding
import com.example.hungryist.ui.fragment.login.LoginFragment
import com.example.hungryist.ui.fragment.register.RegisterFragment

class LoginOrRegisterFragment : Fragment() {

    private val binding by lazy {
        FragmentLoginOrRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.continueAsGuest.setOnClickListener {
            MainActivity.intentFor(requireActivity())
        }

        binding.register.setOnClickListener {
            beginTransaction(RegisterFragment())
        }

        binding.login.setOnClickListener {
            beginTransaction(LoginFragment())
        }
    }

    private fun beginTransaction(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("Login & Register")
            .commit()
    }

}