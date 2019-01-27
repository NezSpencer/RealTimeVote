package com.nezspencer.realtimevote

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.nezspencer.domain.Database
import com.nezspencer.domain.entity.Election

class AppDB(private val firebaseDatabase: DatabaseReference,
            private val status: MutableLiveData<Status>) :
        Database {
    override fun createElection(email: String, election: List<Election>) {
        status.postValue(Status.Running)
        val one = election[0]

        val electionKey = firebaseDatabase.push().key
        one.id = electionKey!!
        one.contestants.forEach {
            it.id = firebaseDatabase.push().key!!
        }
        val map = mutableMapOf<String, Any>()
        map[getSubscribedPath(email, electionKey)] = one
        map[getAllElectionsPath(electionKey)] = election
        Log.e("test", map.toString())
        firebaseDatabase.updateChildren(map) { p0, _ ->
            status.postValue(if (p0 == null) Status.Success else Status.error(p0.message))
        }
    }

    override fun switchNode(path: String) {

    }
}