package com.example.hungryist.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentForgotPasswordBinding
import com.example.hungryist.ui.activity.intro.IntroViewModel
import com.example.hungryist.utils.extension.showToastMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private val binding by lazy {
        FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: IntroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.editEmail.addTextChangedListener {
            binding.textInputEmail.error = null
        }

        binding.btnSend.setOnClickListener {
            binding.editEmail.text.toString().let {
                if (it.isEmpty())
                    binding.textInputEmail.error = getString(R.string.empty_email_info)
                else
                    viewModel.resetPassword(binding.editEmail.text.toString()) { task ->
                        task.addOnSuccessListener {
                            requireContext().showToastMessage(requireContext().getString(R.string.reset_email_sent_message))
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                        task.addOnFailureListener { exception ->
                            binding.textInputEmail.error = exception.message
                        }
                    }
            }
        }
    }
}