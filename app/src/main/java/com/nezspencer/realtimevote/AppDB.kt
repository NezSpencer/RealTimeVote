package com.nezspencer.realtimevote

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.nezspencer.domain.Database
import com.nezspencer.domain.entity.Election

class AppDB(private val firebaseDatabase: DatabaseReference,
            private val status: MutableLiveData<Status>,
            private val eventListener: ValueEventListener) :
        Database {
    override fun createElection(email: String, election: List<Election>) {
        status.postValue(Status.Running)
        val map = mutableMapOf<String, Any>()
        map["/registered/${email.removeDots()}"] = election
        map["/all"] = election
        Log.e("test", map.toString())
        firebaseDatabase.updateChildren(map) { p0, _ ->
            status.postValue(if (p0 == null) Status.Success else Status.error(p0.message))
        }
    }

    override fun switchNode(path: String) {

    }
}