package com.example.hungryist.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.fragment.LoginOrRegisterFragment
import com.example.hungryist.api.APIRequestInterface
import com.example.hungryist.api.Currencies
import com.example.hungryist.api.Currency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var requestInterface: APIRequestInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFragment()
    }

    private fun showFragment() {
        lifecycleScope.launch(Dispatchers.IO) {
            val call = requestInterface.getCurrencies(
                "c9c4e1b99dmsh446d9f65664ddc7p1b6c0ajsn7320c4dff434",
                "restaurants222.p.rapidapi.com"
            )
            call.enqueue(object : Callback<List<Currency>> {
                override fun onResponse(
                    call: Call<List<Currency>>,
                    response: Response<List<Currency>>
                ) {
                    val list = response.body()
                    Log.d("MyTagHere", "onResponse: $list")
                }

                override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                    Toast.makeText(
                        this@IntroActivity,
                        "Error calling currencies!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })

            Log.d("MyTagHere", "showFragment: ")
            Log.d("MyTagHere", "showFragment: ${Currencies().response.request.body.toString()}")
        }
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, LoginOrRegisterFragment())
            .commit()
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, IntroActivity::class.java)
            context.startActivity(intent)
        }
    }
}