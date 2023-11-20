package com.example.hungryist.fragment.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private val binding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        RegisterViewModel(this)
    }

    private var isEmailSection = true




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.register.setOnClickListener(this::onRegisterClicked)
        binding.login.setOnClickListener(this::moveToLoginFragment)
        binding.google.setOnClickListener(this::registerWithGoogle)
        binding.guest.setOnClickListener(this::registerWithGuest)
        binding.facebook.setOnClickListener(this::registerWithGoogle)
    }


    private fun registerWithGuest(view: View) {
        //TODO Register with GUEST
    }

    private fun onRegisterClicked(view:View) {
        if(isPasswordRegular(binding.password.text.toString()))
            return
        if(viewModel.searchValidity(binding,isEmailSection) && searchConfirmation()){
            //TODO move to Main Page fragment
        }
    }

    private fun isPasswordRegular(password: String): Boolean {
        //TODO write here other password requirements
        if(password.length < 6){
            Toast.makeText(requireContext(),"The password can't be less than 6!", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun searchConfirmation():Boolean {
        if(!binding.checkbox.isChecked){
            Toast.makeText(requireContext(),"You must read terms and conditions and accept!", Toast.LENGTH_LONG).show()
            binding.checkbox.error = "Accept it!"
            return false
        }
        return true
    }

    private fun registerWithGoogle(view:View) {
        //TODO register with google
    }

    private fun moveToLoginFragment(view:View) {
        //TODO move to LoginFragment
    }

}