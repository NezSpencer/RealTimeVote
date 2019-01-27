package com.nezspencer.realtimevote.model

import android.arch.lifecycle.LiveData
import android.os.Handler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ElectionListLiveData(private var query: DatabaseReference) :
        LiveData<Pair<DatabaseError?, List<Election>>>(),
        ValueEventListener {

    private var isRemovalPending = false
    private val handler = Handler()
    private val removeRunnable = Runnable {
        if (isRemovalPending) {
            query.removeEventListener(this)
            isRemovalPending = false
        }

    }

    override fun onActive() {
        if (isRemovalPending) {
            handler.removeCallbacks(removeRunnable)
        } else {
            query.addValueEventListener(this)
        }
        isRemovalPending = false
        super.onActive()
    }

    override fun onInactive() {
        handler.postDelayed(removeRunnable, 2000)
        isRemovalPending = true
        super.onInactive()
    }

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(p0: DataSnapshot) {
        val electionList = ArrayList<Election>()
        for (i in p0.children) {
            val election = i.getValue(Election::class.java)
            if (election != null) {
                electionList.add(election)
            }
        }
        value = Pair(null, electionList)
    }

    fun changeNode(reference: DatabaseReference) {
        query.removeEventListener(this)
        query = reference
        query.addValueEventListener(this)

    }

    /*override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        if (p1 != null) {
            value = Pair(p1, listOf())
            return
        }

        val electionList = ArrayList<Election>()
        for (i in p0!!.documentChanges) {
            val election = i.document.toObject(Election::class.java)
            electionList.add(election)
        }
        value = Pair(null, electionList)
    }*/
}