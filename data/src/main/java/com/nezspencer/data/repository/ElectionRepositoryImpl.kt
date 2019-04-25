package com.nezspencer.data.repository

import com.nezspencer.domain.Database
import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class ElectionRepositoryImpl(private val database: Database) : ElectionRepository {
    override suspend fun checkIfUserHasVoted(electionId: String, email: String) = database.hasUserVoted(electionId, email)

    override suspend fun createElection(email: String, election: Election): Boolean {
        return database.createElection(email, election)
    }

    override suspend fun vote(email: String, contestantId: String, electionId: String,
                              electionTitle: String, electoralSeat: String, endDate: Long):
            Boolean {
        return database.vote(email, contestantId, electionId, electionTitle, electoralSeat, endDate)
    }

    override fun getElectionResults() {
        database.getElectionResults()
    }
}