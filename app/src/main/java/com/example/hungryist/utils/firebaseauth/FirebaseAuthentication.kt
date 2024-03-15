package com.example.hungryist.utils.firebaseauth

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.IntentSenderRequest
import com.example.hungryist.R
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.UserManager
import com.example.hungryist.utils.extension.showToastMessage
import com.facebook.CallbackManager
import com.facebook.ProfileManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class FirebaseAuthentication(
    private val activity: Activity,
    private val auth: FirebaseAuth,
    private val facebookCallbackManager: CallbackManager,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userManager: UserManager,
) {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signUpRequest: BeginSignInRequest


    //Register and sign in with Google

    fun onRegisterWithGoogleClickListener(
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>,
    ) {
        initializeGoogleItems()
        oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener(
                activity
            ) {
                val intentSenderRequest =
                    IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                activityResultLauncher.launch(intentSenderRequest)
            }
            .addOnFailureListener(activity) {
                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    private fun initializeGoogleItems() {
        oneTapClient = Identity.getSignInClient(activity)
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(activity.getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }


    fun firebaseIdTokenForGoogleAuth(
        credential: AuthCredential,
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    userManager.createProfile(auth.currentUser?.email, true)
                    registerAccepted()
                } else {
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    //Register with Facebook

    fun onRegisterWithFacebookClick(activityResultRegistryOwner: ActivityResultRegistryOwner) {
        LoginManager.getInstance()
            .logInWithReadPermissions(
                activityResultRegistryOwner,
                facebookCallbackManager,
                listOf("public_profile")
            );
    }

    // Register with Phone number

    fun registerWithPhoneNumber(
        phoneNumber: String,
        callback: (String?) -> Unit,
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(registerWithNumberCallback())
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun registerWithNumberCallback(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                activity.showToastMessage("Verified $credential")
//                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                val message: String = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        "Invalid request"
                    }

                    is FirebaseTooManyRequestsException -> {
                        "The SMS quota for the project has been exceeded"
                    }

                    else -> {
                        e.message.toString()
                    }
                }

                activity.showToastMessage(message)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("MyTagHere", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                val storedVerificationId = verificationId
                val resendToken = token
            }
        }
    }

    //Register with phone number and password

    fun registerWithPhoneNumberAndPassword(
        phoneNumber: String,
        password: String,
        errorCallback: (String?) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword("$phoneNumber@hungryist.com", password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    registerAccepted()
                    userManager.createProfile(phoneNumber, false)
                } else
                    errorCallback(
                        task.exception!!.message.toString().replace("email address", "phone number")
                    )
            }
    }


//Register with email and password

    fun registerWithEmailAndPassword(
        email: String,
        password: String,
        callback: (String?) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    userManager.createProfile(email, true)
                    registerAccepted()
                } else
                    callback(task.exception!!.message.toString())
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (String?) -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    registerAccepted()
                } else {
                    callback(task.exception!!.message.toString())
                }
            }
    }

    private fun registerAccepted() {
        val firebaseUser = auth.currentUser
        sharedPreferencesManager.setUserId(firebaseUser?.uid)
        MainActivity.intentFor(activity)
        Log.d("MyTagHerenm", "signInWithEmailAndPassword: ${firebaseUser?.uid}")
        sharedPreferencesManager.setRegistered(true)
    }
}