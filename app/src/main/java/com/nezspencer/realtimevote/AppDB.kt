package com.nezspencer.realtimevote

import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.nezspencer.domain.Database
import com.nezspencer.domain.entity.Election
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppDB(private val firebaseDatabase: DatabaseReference,
            private val status: MutableLiveData<Status>) :
        Database {
    override suspend fun createElection(email: String, election: Election): Boolean {
        status.postValue(Status.Running)

        val electionKey = firebaseDatabase.push().key
        election.id = electionKey!!
        election.contestants.forEach {
            it.id = firebaseDatabase.push().key!!
        }
        val map = mutableMapOf<String, Any>()
        map[getSubscribedPath(email, electionKey)] = election
        map[getAllElectionsPath(electionKey)] = election
        return suspendCoroutine {
            firebaseDatabase.updateChildren(map) { p0, _ ->
                status.postValue(if (p0 == null) Status.Success else Status.error(p0.message))
                it.resume(p0 == null)
            }
        }
    }

    override suspend fun hasUserVoted(electionId: String, email: String): Boolean {
        return false
    }

    override suspend fun vote(email: String, contestantId: String, electionId: String): Boolean {
        status.postValue(Status.Running)
        val map = mutableMapOf<String, Any?>()
        map[getResultsPath(electionId, contestantId)] = email
        map[getVoteStatusPath(electionId, email.removeDots())] = "voted"
        map[getSubscribedPath(email.removeDots(), electionId)] = null
        return suspendCoroutine {
            firebaseDatabase.updateChildren(map) { p0, _ ->
                status.postValue(if (p0 == null) Status.Success else Status.error(p0.message))
                it.resume(p0 == null)
            }
        }
    }
}