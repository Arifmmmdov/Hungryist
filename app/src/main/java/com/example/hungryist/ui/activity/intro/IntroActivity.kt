package com.example.hungryist.ui.activity.intro

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.ui.fragment.login_or_register.LoginOrRegisterFragment
import com.example.hungryist.utils.extension.showToastMessage
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val activityResultLauncher: ActivityResultLauncher<IntentSenderRequest> by lazy {

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

    @Inject
    lateinit var viewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeItems(this, activityResultLauncher)
        setContentView(binding.root)
        showFragment()
    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, LoginOrRegisterFragment())
            .commit()
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}