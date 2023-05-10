package com.ho.chatty.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ho.chatty.R
import com.ho.chatty.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth

    // ActivityResultLauncher
    private val signIn: ActivityResultLauncher<Intent> =
        registerForActivityResult(FirebaseAuthUIActivityResultContract(), this::onSignInResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()

        if (Firebase.auth.currentUser == null) {
            val signInIntent =
                AuthUI.getInstance().createSignInIntentBuilder().setLogo(R.mipmap.ic_launcher)
                    .setAvailableProviders(
                        listOf(
                            AuthUI.IdpConfig.EmailBuilder().build(),
                            AuthUI.IdpConfig.GoogleBuilder().build()
                        )
                    ).build()
            signIn.launch(signInIntent)
        } else goToMainActivity()
    }

    private fun signIn() {
        // TODO: implement
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Sign in success", Toast.LENGTH_SHORT).show()
            goToMainActivity()
        } else {
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
            val response = result.idpResponse
            if (response == null) {
                Log.w(TAG, "Sign in cancelled")
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Log.w(TAG, "Sign in error", response.error)
                Toast.makeText(this, "Sign in error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}