package com.nezspencer.realtimevote.model

import com.google.firebase.auth.FirebaseAuth

interface RepositoryContract {
    fun getVoters()

    fun getContestants()

    fun onAppAuthStateChanged(auth: FirebaseAuth)

    fun onElectionFetched(elections: List<Election>)

}