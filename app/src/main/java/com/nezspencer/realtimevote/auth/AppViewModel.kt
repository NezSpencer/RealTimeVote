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
import com.nezspencer.realtimevote.*
import com.nezspencer.realtimevote.model.Election

class AppViewModel(application: Application) : AndroidViewModel(application), ValueEventListener,
        FirebaseAuth.AuthStateListener {

    init {
        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    val electionUploadStatus = MutableLiveData<Status>()
    private val database = FirebaseDatabase.getInstance().reference
    val appDB = AppDB(database, electionUploadStatus)
    val repository = ElectionRepositoryImpl(appDB)
    var electionData = MutableLiveData<Pair<DatabaseError?, List<Election>>>()
    var authLivedata = MutableLiveData<FirebaseAuth>()

    fun createElection(email: String) {
        val postElection = CreateElectionUsecase(repository,
                App.elections.toDomainElectionModel(),
                email)
        postElection()
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