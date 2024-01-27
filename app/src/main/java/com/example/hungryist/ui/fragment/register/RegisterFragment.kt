package com.example.hungryist.ui.fragment.register

import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentRegisterBinding
import com.example.hungryist.ui.activity.intro.IntroViewModel
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.ui.fragment.login.LoginFragment
import com.example.hungryist.utils.extension.showToastMessage
import com.google.android.material.tabs.TabLayout
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
        setViews()
        setObservers()
        return binding.root
    }

    private fun setViews() {
        setUpTabLayout()

        setTermsAndConditionText()


    }

    private fun setUpTabLayout() {
        val emailTab = binding.tabLayout.newTab().setText(R.string.e_mail)
        val phoneNumberTab = binding.tabLayout.newTab().setText(R.string.phone_number)

        binding.tabLayout.apply {
            addTab(emailTab)
            addTab(phoneNumberTab)
            getTabAt(0)?.select()
        }
    }

    private fun setObservers() {
        viewModel.isEmailSelected.observe(requireActivity()) {
            setTabSelectedListener(it)
        }
    }

    private fun setTabSelectedListener(isEmailSelected: Boolean) {
        if (isEmailSelected){
            binding.textInputMain.hint = getString(R.string.e_mail)
            binding.userName.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        else{
            binding.textInputMain.hint = getString(R.string.phone_number)
            binding.userName.inputType = InputType.TYPE_CLASS_PHONE
        }
    }

    private fun setListeners() {
        binding.register.setOnClickListener(this::onRegisterClicked)
        binding.login.setOnClickListener(this::moveToLoginFragment)
        binding.google.setOnClickListener(this::registerWithGoogle)
        binding.guest.setOnClickListener(this::registerWithGuest)
        binding.facebook.setOnClickListener(this::registerWithFacebook)
        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener())
    }

    private fun tabSelectedListener(): TabLayout.OnTabSelectedListener {
        return object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.registerTabSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Handle tab unselection if needed
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Handle tab reselection if needed
            }
        }
    }


    private fun registerWithGuest(view: View) {
        MainActivity.intentFor(requireActivity())
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
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
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
                29, 50, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(requireContext().getColor(R.color.secondary_color)),
                29, 50, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(UnderlineSpan(), 29, 50, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.termsAndCondition.apply {
            movementMethod = LinkMovementMethod.getInstance()
            text = spannableString
        }
    }

}