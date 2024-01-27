package com.example.hungryist.ui.fragment.register

import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentRegisterBinding
import com.example.hungryist.ui.activity.intro.IntroViewModel
import com.example.hungryist.ui.fragment.login.LoginFragment
import com.example.hungryist.utils.extension.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val binding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: IntroViewModel

    private var isEmailSection = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        setTermsAndConditionText()
        return binding.root
    }


    private fun setListeners() {
        binding.register.setOnClickListener(this::onRegisterClicked)
        binding.login.setOnClickListener(this::moveToLoginFragment)
        binding.google.setOnClickListener(this::registerWithGoogle)
        binding.guest.setOnClickListener(this::registerWithGuest)
        binding.facebook.setOnClickListener(this::registerWithFacebook)
    }


    private fun registerWithGuest(view: View) {
        //TODO Register with GUEST
    }


    private fun registerWithFacebook(view: View) {
        viewModel.registerWithFacebook(requireActivity())
    }

    private fun onRegisterClicked(view: View) {
        if (isPasswordRegular(binding.password.text.toString()))
            return
        if (viewModel.searchValidity(binding, isEmailSection) && searchConfirmation()) {
            //TODO move to Main Page fragment
        }
    }

    private fun isPasswordRegular(password: String): Boolean {
        //TODO write here other password requirements
        if (password.length < 6) {
            requireContext().showToastMessage("The password can't be less than 6!")
            return false
        }
        return true
    }

    private fun searchConfirmation(): Boolean {
        if (!binding.checkbox.isChecked) {
            requireContext().showToastMessage("You must read terms and conditions and accept!")
            binding.checkbox.error = "Accept it!"
            return false
        }
        return true
    }

    private fun registerWithGoogle(view: View) {
        viewModel.registerWithGoogle()
    }

    private fun moveToLoginFragment(view: View) {
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    private fun setTermsAndConditionText() {
        val spannableString = SpannableString(
            requireContext().getString(R.string.terms_and_conditions)
        ).apply {
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        //TODO move to terms and conditions view
                    }
                },
                23, 46, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(requireContext().getColor(R.color.secondary_color)),
                23, 46, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(UnderlineSpan(), 23, 46, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.termsAndCondition.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = spannableString
        }
    }

}