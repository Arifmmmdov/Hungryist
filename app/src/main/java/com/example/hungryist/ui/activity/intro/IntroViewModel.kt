package com.example.hungryist.ui.activity.intro

import android.app.Activity
import android.content.Context
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.IntentSenderRequest
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentRegisterBinding
import com.example.hungryist.utils.CommonHelper
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.firebaseutils.FirebaseAuthentication
import com.facebook.CallbackManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(val context: Context) : ViewModel() {
    private val _isEmailSelected = MutableLiveData<Boolean>()
    val isEmailSelected: LiveData<Boolean> = _isEmailSelected

    private lateinit var firebaseAuth: FirebaseAuthentication

    private val sharedPreferencesManager: SharedPreferencesManager by lazy {
        SharedPreferencesManager(context.getSharedPreferences("", Context.MODE_PRIVATE))
    }

    private lateinit var resultLauncherForGoogle: ActivityResultLauncher<IntentSenderRequest>


    fun initializeItems(
        activity: Activity,
        resultLauncherForGoogle: ActivityResultLauncher<IntentSenderRequest>,
        facebookCallbackManager: CallbackManager,
    ) {
        firebaseAuth =
            FirebaseAuthentication(activity, FirebaseAuth.getInstance(), facebookCallbackManager)
        this.resultLauncherForGoogle = resultLauncherForGoogle
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
                ds.linkColor = ContextCompat.getColor(context, R.color.secondary_color)
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

    fun registerWithGoogle() {
        firebaseAuth.onRegisterWithGoogleClickListener(resultLauncherForGoogle)
    }

    fun registerWithEmailAndPassword(email: String, password: String) {
        firebaseAuth.registerWithEmailAndPassword(email, password) {

        }
    }

    fun registerWithPhoneNumber(
        phoneNumber: String,
        password: String,
    ): Boolean? {
        val isValid = CommonHelper.isValidPhoneNumber(
            phoneNumber.substring(4),
            phoneNumber.substring(0, 3)
        )?.isValid
        if (isValid == true) {
            firebaseAuth.registerWithPhoneNumberAndPassword(phoneNumber, password)
        } else
            Toast.makeText(context, "Phone number is not valid", Toast.LENGTH_LONG).show()
        return isValid
    }

    fun firebaseAuthWithFacebook(idToken: String) {
        val credential = FacebookAuthProvider.getCredential(idToken)
        firebaseAuth.firebaseIdTokenForGoogleAuth(credential, sharedPreferencesManager)
    }

    fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.firebaseIdTokenForGoogleAuth(credential, sharedPreferencesManager)
    }

    fun registerWithFacebook(activityResultRegistryOwner: ActivityResultRegistryOwner) {
        firebaseAuth.onRegisterWithFacebookClick(activityResultRegistryOwner)
    }

    fun registerTabSelected(position: Int) {
        _isEmailSelected.value = position == 0
    }


}
