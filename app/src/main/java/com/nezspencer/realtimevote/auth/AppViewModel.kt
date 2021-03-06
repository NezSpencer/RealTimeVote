package com.nezspencer.realtimevote.auth

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nezspencer.data.repository.ElectionRepositoryImpl
import com.nezspencer.domain.usecase.CreateElectionUsecase
import com.nezspencer.domain.usecase.VoteContestantUsecase
import com.nezspencer.realtimevote.AppDB
import com.nezspencer.realtimevote.Status
import com.nezspencer.realtimevote.getSubscribedPath
import com.nezspencer.realtimevote.model.Election
import com.nezspencer.realtimevote.toDomainElectionModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application), ValueEventListener,
        FirebaseAuth.AuthStateListener {

    init {
        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    val electionUploadStatus = MutableLiveData<Status>()
    private val database = FirebaseDatabase.getInstance().reference
    private val appDB = AppDB(database, electionUploadStatus)
    private val repository = ElectionRepositoryImpl(appDB)
    var electionData = MutableLiveData<Pair<DatabaseError?, List<Election>>>()
    var authLivedata = MutableLiveData<FirebaseAuth>()

    fun createElection(email: String, election: Election) {
        val postElection = CreateElectionUsecase(repository,
                election.toDomainElectionModel(),
                email)
        GlobalScope.launch { postElection() }
    }

    fun voteContestant(email: String, electionId: String, contestantId: String) {
        val voteUsecase = VoteContestantUsecase(repository, contestantId, email, electionId)
        GlobalScope.launch { voteUsecase() }
    }

    fun resetUploadStatus() {
        electionUploadStatus.value = Status.Refresh
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
        electionData.value = Pair(null, electionList)
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        authLivedata.value = p0

        p0.currentUser?.let {
            database.child(getSubscribedPath(it.email!!))
                    .addValueEventListener(this)
        }

    }
}