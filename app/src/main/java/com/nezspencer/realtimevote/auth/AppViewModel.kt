package com.nezspencer.realtimevote.auth

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nezspencer.realtimevote.App
import com.nezspencer.realtimevote.Status
import com.nezspencer.realtimevote.model.RepositoryContract

class AppViewModel : ViewModel(), RepositoryContract {

    val authLiveData = MutableLiveData<FirebaseAuth>()
    val electionUploadStatus = MutableLiveData<Status>()
    private val database = FirebaseDatabase.getInstance()

    override fun getVoters() {
    }

    override fun getContestants() {

    }

    override fun onAppAuthStateChanged(auth: FirebaseAuth) {
        authLiveData.value = auth
    }

    fun createElection() {
        electionUploadStatus.postValue(Status.Running)
        val map = mutableMapOf<String, Any>()
        map["/Elections/"] = App.elections
        database.reference.updateChildren(map) { p0, _ ->
            electionUploadStatus.postValue(if (p0 == null) Status.Success else Status.error(p0.message))
        }
    }

    fun resetUploadStatus() {
        electionUploadStatus.value = Status.Refresh
    }

}