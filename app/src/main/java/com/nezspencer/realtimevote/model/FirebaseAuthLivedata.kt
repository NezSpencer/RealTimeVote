package com.nezspencer.realtimevote.model

import android.arch.lifecycle.LiveData
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthLivedata(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) :
        LiveData<FirebaseAuth>(), FirebaseAuth.AuthStateListener {

    private val handler = Handler()
    private var isRemovePending = false
    private val removeListener = Runnable {
        auth.removeAuthStateListener(this)
        isRemovePending = false
    }

    override fun onInactive() {
        handler.postDelayed(removeListener, 2000)
        isRemovePending = true
        super.onInactive()
    }

    override fun onActive() {
        if (isRemovePending) {
            handler.removeCallbacks(removeListener)
        } else {
            auth.addAuthStateListener(this)
        }
        isRemovePending = false
        super.onActive()
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        value = p0
    }
}