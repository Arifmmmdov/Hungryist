package com.example.hungryist.ui.activity.intro

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.utils.CommonHelper
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.firebaseauth.FirebaseAuthentication
import com.facebook.CallbackManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(val context: Context,val sharedPreferencesManager:SharedPreferencesManager) : ViewModel() {
    private val _isEmailSelected = MutableLiveData<Boolean>()
    val isEmailSelected: LiveData<Boolean> = _isEmailSelected

    val registerErrorMessage = MutableLiveData<String>()
    val loginErrorMessage = MutableLiveData<String>()

    private lateinit var firebaseAuth: FirebaseAuthentication

    private lateinit var resultLauncherForGoogle: ActivityResultLauncher<IntentSenderRequest>


    fun initializeItems(
        activity: Activity,
        resultLauncherForGoogle: ActivityResultLauncher<IntentSenderRequest>,
        facebookCallbackManager: CallbackManager,
    ) {
        firebaseAuth =
            FirebaseAuthentication(
                activity,
                FirebaseAuth.getInstance(),
                facebookCallbackManager,
                sharedPreferencesManager
            )
        this.resultLauncherForGoogle = resultLauncherForGoogle
    }

    fun registerWithGoogle() {
        firebaseAuth.onRegisterWithGoogleClickListener(resultLauncherForGoogle)
    }

    fun firebaseAuthWithFacebook(idToken: String) {
        val credential = FacebookAuthProvider.getCredential(idToken)
        firebaseAuth.firebaseIdTokenForGoogleAuth(credential)
    }

    fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.firebaseIdTokenForGoogleAuth(credential)
    }

    fun registerWithFacebook(activityResultRegistryOwner: ActivityResultRegistryOwner) {
        firebaseAuth.onRegisterWithFacebookClick(activityResultRegistryOwner)
    }

    fun registerTabSelected(position: Int) {
        _isEmailSelected.value = position == 0
    }

    fun checkMainText(typedText: String): Boolean {

        return (isEmailSelected.value == true && CommonHelper.isValidEmail(typedText) == true)
                || (isEmailSelected.value == false && CommonHelper.isValidPhoneNumber(
            typedText.takeLast(
                9
            ), "994"
        ).isValid)

    }

    fun isValidPassword(password: String?): Boolean {
        if (password != null) {
            return password.length > 6
        }
        return true
    }


    fun register(mainText: String, password: String) {
        if (isEmailSelected.value!!) {
            firebaseAuth.registerWithEmailAndPassword(
                mainText,
                password
            ) {
                registerErrorMessage.value = it
            }
        } else
            firebaseAuth.registerWithPhoneNumberAndPassword(
                mainText.take(9),
                password
            ) {
                registerErrorMessage.value = it
            }

    }

    fun login(emailOrPhoneNumber: String, password: String) {
        if (emailOrPhoneNumber.contains("@"))
            firebaseAuth.signInWithEmailAndPassword(
                emailOrPhoneNumber,
                password
            ) {
                loginErrorMessage.value = it
            }
        else
            firebaseAuth.signInWithEmailAndPassword(
                "$emailOrPhoneNumber@hungryist.com",
                password
            ) {
                loginErrorMessage.value = it
            }
    }

    fun checkLoginValidity(emailOrPhoneNumber: String): Boolean {
        return CommonHelper.isValidEmail(emailOrPhoneNumber) == true
                || CommonHelper.isValidPhoneNumber(
            emailOrPhoneNumber.takeLast(
                9
            ), "994"
        ).isValid
    }

}
