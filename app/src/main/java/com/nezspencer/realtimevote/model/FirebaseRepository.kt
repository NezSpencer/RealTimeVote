package com.nezspencer.realtimevote.model

import com.google.firebase.auth.FirebaseAuth

class FirebaseRepository(private val contract: RepositoryContract) : FirebaseAuth.AuthStateListener,
        LifecycleListener {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun attachListener() {
        firebaseAuth.addAuthStateListener(this)
    }

    override fun detachListener() {
        firebaseAuth.removeAuthStateListener(this)
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        contract.onAppAuthStateChanged(p0)
    }
}