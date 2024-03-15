package com.example.hungryist.ui.activity.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hungryist.databinding.ActivitySplashScreenBinding
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.ui.dialog.UpdateInfoDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.checkApplicationVersion {
            if (it)
                checkRegistered()
            else
                showDialog()

        }
    }

    private fun showDialog() {
        UpdateInfoDialog(this).show()
    }

    private fun checkRegistered() {
        viewModel.checkRegistered { isRegistered ->
            if (isRegistered)
                MainActivity.intentFor(this)
            else
                IntroActivity.intentFor(this)
        }
    }

}