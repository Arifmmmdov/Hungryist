package com.example.hungryist.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentLoginBinding
import com.example.hungryist.ui.activity.intro.IntroViewModel
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.ui.fragment.ForgotPasswordFragment
import com.example.hungryist.ui.fragment.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: IntroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.loginErrorMessage.observe(requireActivity()) {
            binding.textInputMain.error = it
        }
    }

    private fun setListeners() {
        binding.google.setOnClickListener {
            viewModel.registerWithGoogle()
        }

        binding.logIn.setOnClickListener {
            login()
        }

        binding.facebook.setOnClickListener {
            viewModel.registerWithFacebook(requireActivity())
        }

        binding.guest.setOnClickListener {
            MainActivity.intentFor(requireActivity())
        }

        binding.register.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment())
                .commit()
        }

        binding.forgotPassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ForgotPasswordFragment())
                .addToBackStack("Forgot Password")
                .commit()
        }

    }

    private fun login() {
        binding.textInputMain.isErrorEnabled = false
        binding.textInputPassword.isErrorEnabled = false

        if (!viewModel.checkLoginValidity(binding.editMain.text.toString()))
            binding.textInputMain.error = "This is not valid!"
        else if (!viewModel.isValidPassword(binding.editPassword.text.toString()))
            binding.textInputPassword.error = requireContext().getString(R.string.less_than_6)
        else
            viewModel.login(binding.editMain.text.toString(), binding.editPassword.text.toString())
    }
}