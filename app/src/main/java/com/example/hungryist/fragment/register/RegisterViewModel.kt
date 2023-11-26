package com.example.hungryist.fragment.register

import android.content.Context
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentRegisterBinding
import com.example.hungryist.helper.CommonHelper

class RegisterViewModel(context: LifecycleOwner) : ViewModel() {
    private val _liveData = MutableLiveData<String>()
    private val liveData: LiveData<String> = _liveData

    init {
        liveData.observe(context) {

        }
    }

    fun setClickableSpannableView(termsAndConditions: TextView, context: Context) {
        termsAndConditions.movementMethod = LinkMovementMethod.getInstance()
        termsAndConditions.setText(getSpannableString(context), TextView.BufferType.SPANNABLE)
    }

    private fun getSpannableString(context: Context): SpannableString {
        val spannableString =
            SpannableString(context.resources.getString(R.string.terms_and_conditions))
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.linkColor = ContextCompat.getColor(context, R.color.purple_700)
                ds.isUnderlineText = true
            }

            override fun onClick(p0: View) {
                //TODO move to "Terms and conditions page"
            }
        }
        spannableString.setSpan(clickableSpan, 29, 50, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableString
    }

    fun searchValidity(binding: FragmentRegisterBinding, emailSection: Boolean): Boolean {
        return if (emailSection) {
            searchEmailValidity(binding.userName.text.toString())
        } else {
            searchPhoneValidity(binding.userName.text.toString())
        }
    }

    private fun searchEmailValidity(email: String): Boolean = CommonHelper.isValidEmail(email)
    private fun searchPhoneValidity(phoneNumber: String): Boolean =
        CommonHelper.isValidPhoneNumber(phoneNumber, "994")!!.isValid


}
