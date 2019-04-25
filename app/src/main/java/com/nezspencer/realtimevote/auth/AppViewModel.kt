package com.nezspencer.realtimevote.auth

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nezspencer.data.repository.ElectionRepositoryImpl
import com.nezspencer.domain.usecase.CreateElectionUsecase
import com.nezspencer.domain.usecase.GetVoteResultListUsecase
import com.nezspencer.domain.usecase.VoteContestantUsecase
import com.nezspencer.realtimevote.AppDB
import com.nezspencer.realtimevote.Status
import com.nezspencer.realtimevote.getSubscribedPath
import com.nezspencer.realtimevote.model.Election
import com.nezspencer.realtimevote.pojo.ResultItem
import com.nezspencer.realtimevote.toDomainElectionModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application), ValueEventListener,
        FirebaseAuth.AuthStateListener, AppDB.LivedataInteractions {

    init {
        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    val electionUploadStatus = MutableLiveData<Status>()
    private val database = FirebaseDatabase.getInstance().reference
    private val appDB = AppDB(database, this)
    private val repository = ElectionRepositoryImpl(appDB)
    var electionData = MutableLiveData<Pair<DatabaseError?, List<Election>>>()
    var resultsLiveData = MutableLiveData<Pair<DatabaseError?, List<ResultItem>>>()
    var authLivedata = MutableLiveData<FirebaseAuth>()

    fun createElection(email: String, election: Election) {
        val postElection = CreateElectionUsecase(repository,
                election.toDomainElectionModel(),
                email)
        GlobalScope.launch { postElection() }
    }

    fun voteContestant(email: String, election: Election, contestantId: String) {
        val voteUsecase = VoteContestantUsecase(repository, contestantId, email, election.toDomainElectionModel())
        GlobalScope.launch { voteUsecase() }
    }

    fun seeVoteResults() {
        val getResultList = GetVoteResultListUsecase(repository)
        getResultList()
    }

    fun resetUploadStatus() {
        electionUploadStatus.value = Status.Refresh
    }

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(p0: DataSnapshot) {
        val electionList = ArrayList<Election>()
        val resultsList = ArrayList<ResultItem>()
        for (i in p0.children) {
            val election = i.getValue(Election::class.java)
            val electionResult = i.getValue(ResultItem::class.java)
            if (electionResult != null && !TextUtils.isEmpty(electionResult.metaData.electionId)) {
                //results data
                resultsList.add(electionResult)
            } else if (election != null && !TextUtils.isEmpty(election.id)) {
                electionList.add(election)
            }
        }
        if (electionList.size > 0)
            electionData.value = Pair(null, electionList)
        if (resultsList.size > 0)
            resultsLiveData.value = Pair(null, resultsList)
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        authLivedata.value = p0

        p0.currentUser?.let {
            onPathChangedUpdate(getSubscribedPath(it.email!!))
        }

    }

    override fun onStatusLivedataUpdate(status: Status) {
        electionUploadStatus.postValue(status)
    }

    override fun onPathChangedUpdate(newPath: String) {
        database.child(newPath)
                .addValueEventListener(this)
    }
}