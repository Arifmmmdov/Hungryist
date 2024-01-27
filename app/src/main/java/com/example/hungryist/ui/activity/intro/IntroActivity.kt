package com.example.hungryist.ui.activity.intro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.ui.fragment.login_or_register.LoginOrRegisterFragment
import com.example.hungryist.utils.extension.showToastMessage
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
open class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val googleActivityResultLauncher: ActivityResultLauncher<IntentSenderRequest> by lazy {

        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val credential =
                    Identity.getSignInClient(this).getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                viewModel.firebaseAuthWithGoogle(idToken)
            } else {
                this.showToastMessage("Result is not successful with Google!")
            }

        }
    }

    private lateinit var facebookCallbackManager: CallbackManager

    @Inject
    lateinit var viewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerFacebookCallback()
        setContentView(binding.root)
        showFragment()
        viewModel.initializeItems(this, googleActivityResultLauncher, facebookCallbackManager)
    }

    private fun registerFacebookCallback() {
        facebookCallbackManager = create()

        LoginManager.getInstance()
            .registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    viewModel.firebaseAuthWithFacebook(result.accessToken.token)
                }

                override fun onCancel() {
                    this@IntroActivity.showToastMessage("App code for login cancellation")
                }

                override fun onError(error: FacebookException) {
                    this@IntroActivity.showToastMessage(error.message.toString())
                }
            })
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, LoginOrRegisterFragment())
            .commit()
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}