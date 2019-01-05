package com.nezspencer.realtimevote.auth

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.nezspencer.realtimevote.App
import com.nezspencer.realtimevote.Status
import com.nezspencer.realtimevote.getUserEmail
import com.nezspencer.realtimevote.model.ElectionListLiveData
import com.nezspencer.realtimevote.model.FirebaseAuthLivedata
import com.nezspencer.realtimevote.removeDots

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val authLiveData = FirebaseAuthLivedata()
    val electionUploadStatus = MutableLiveData<Status>()
    private val database = FirebaseDatabase.getInstance().reference.child("Election")
    val electionList = ElectionListLiveData(database
            .child("registered/${getUserEmail(application).removeDots()}"))

    fun createElection(email: String) {
        electionUploadStatus.postValue(Status.Running)
        val map = mutableMapOf<String, Any>()
        map["/registered/${email.removeDots()}"] = App.elections
        map["/all"] = App.elections
        Log.e("test", map.toString())
        database.updateChildren(map) { p0, _ ->
            electionUploadStatus.postValue(if (p0 == null) Status.Success else Status.error(p0.message))
        }
    }

    fun resetUploadStatus() {
        electionUploadStatus.value = Status.Refresh
    }
}