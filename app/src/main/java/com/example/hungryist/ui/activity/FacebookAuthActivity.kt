package com.example.hungryist.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.utils.extension.showToastMessage
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.Identity


class FacebookAuthActivity : IntroActivity() {

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> by lazy {

        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                callbackManager.onActivityResult(result.resultCode, result.resultCode, result.data)
            } else {
                this.showToastMessage("Result is not successful with Google!")
            }

        }
    }

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code for successful login
                    loginResult.accessToken
                }

                override fun onCancel() {
                    // App code for login cancellation
                }

                override fun onError(exception: FacebookException) {
                    // App code for login error
                }
            })
    }
}