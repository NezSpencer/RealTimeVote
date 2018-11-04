package com.nezspencer.realtimevote.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.nezspencer.realtimevote.BuildConfig
import com.nezspencer.realtimevote.Dashboard.HomeFragment
import com.nezspencer.realtimevote.R
import com.nezspencer.realtimevote.model.AppActivityLifecycleObserver
import com.nezspencer.realtimevote.model.FirebaseRepository

class AppActivity : AppCompatActivity() {

    private lateinit var firebaseRepository: FirebaseRepository
    private lateinit var appViewModel: AppViewModel
    private lateinit var activityObserver: AppActivityLifecycleObserver
    val RC_SIGN_IN = 1003
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        firebaseRepository = FirebaseRepository(appViewModel)
        activityObserver = AppActivityLifecycleObserver(firebaseRepository, this.lifecycle)
        this.lifecycle.addObserver(activityObserver)
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
                onAuthSuccessful()
            }
        })

    }

    private fun onAuthSuccessful() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commitAllowingStateLoss()
    }
}
