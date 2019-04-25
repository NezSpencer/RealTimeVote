package com.nezspencer.realtimevote

import com.google.firebase.database.DatabaseReference
import com.nezspencer.domain.Database
import com.nezspencer.domain.entity.Election
import com.nezspencer.realtimevote.pojo.MetaData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppDB(private val firebaseDatabase: DatabaseReference,
            private val listener: LivedataInteractions) :
        Database {
    override suspend fun createElection(email: String, election: Election): Boolean {
        listener.onStatusLivedataUpdate(Status.Running)

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
                listener.onStatusLivedataUpdate(if (p0 == null) Status.Success else Status.error(p0.message))
                it.resume(p0 == null)
            }
        }
    }

    override suspend fun hasUserVoted(electionId: String, email: String): Boolean {
        return false
    }

    override suspend fun vote(email: String,
                              contestantId: String,
                              electionId: String,
                              electionTitle: String,
                              electoralSeat: String,
                              endDate: Long): Boolean {
        listener.onStatusLivedataUpdate(Status.Running)
        val extra = MetaData(electionId, electionTitle, electoralSeat, endDate)
        val map = mutableMapOf<String, Any?>()
        val voteKey = firebaseDatabase.push().key!!
        map[getContestantVotePath(electionId, contestantId, voteKey)] = email
        map[getVoteExtraPath(electionId)] = extra
        map[getVoteStatusPath(electionId, email.removeDots())] = "voted"
        map[getSubscribedPath(email.removeDots(), electionId)] = null
        return suspendCoroutine {
            firebaseDatabase.updateChildren(map) { p0, _ ->
                listener.onStatusLivedataUpdate(if (p0 == null) Status.Success else Status.error(p0.message))
                it.resume(p0 == null)
            }
        }
    }

    override fun getElectionResults() {
        listener.onStatusLivedataUpdate(Status.Running)
        val dbPath = getResultListPath()
        listener.onPathChangedUpdate(dbPath)

    }

    interface LivedataInteractions {
        fun onStatusLivedataUpdate(status: Status)

        fun onPathChangedUpdate(newPath: String)
    }
}