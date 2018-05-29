package com.nezspencer.realtimevote.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.nezspencer.realtimevote.BuildConfig
import com.nezspencer.realtimevote.Dashboard.HomeFragment
import com.nezspencer.realtimevote.R

class AuthActivity : AppCompatActivity(), AuthContract.View {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authListener : FirebaseAuth.AuthStateListener
    val RC_SIGN_IN = 1003
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        firebaseAuth = FirebaseAuth.getInstance()
        authListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if (user == null)
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setLogo(R.drawable.ic_launcher_foreground)
                                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                .setAvailableProviders(
                                        mutableListOf(AuthUI.IdpConfig.GoogleBuilder().build(),
                                                AuthUI.IdpConfig.PhoneBuilder().build()))
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN)
            else {
                onAuthSuccessful()
            }
        }
        firebaseAuth.addAuthStateListener(authListener)

    }

    override fun onActivityRes() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAuthSuccessful() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
    }

    override fun onAuthFailure(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
