package com.nezspencer.realtimevote

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nezspencer.realtimevote.Dashboard.HomeFragment
import com.nezspencer.realtimevote.auth.AppViewModel
import com.nezspencer.realtimevote.auth.AppViewModelFactory

class AppActivity : AppCompatActivity() {

    private lateinit var appViewModel: AppViewModel
    val RC_SIGN_IN = 1003

    companion object {
        const val KEY_EMAIL = "user_email_"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        appViewModel = ViewModelProviders.of(this, AppViewModelFactory(application)).get(AppViewModel::class.java)
        appViewModel.authLiveData.observe(this, Observer<FirebaseAuth> {
            val user = it?.currentUser
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
                onAuthSuccessful(user)
            }
        })

    }

    private fun onAuthSuccessful(user: FirebaseUser) {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(KEY_EMAIL, user.email).apply()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commitAllowingStateLoss()
    }

    fun getViewModel() = appViewModel
}
