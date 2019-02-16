package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class FakeElectionRepo : ElectionRepository {
    private val database = mutableMapOf<String, MutableList<Election>>()
    private val voteRecord = mutableMapOf<String, String>()
    override suspend fun checkIfUserHasVoted(electionId: String, email: String): Boolean {
        return voteRecord[electionId] == email
    }

    override suspend fun createElection(email: String, election: Election): Boolean {
        val key = "subscribed/$email"
        val list = database.getOrDefault(key, mutableListOf())
        list.add(election)
        database[key] = list

        return database.containsKey(key)
    }

    override suspend fun vote(email: String, contestantId: String, electionId: String): Boolean {
        voteRecord[electionId] = email
        return true
    }
}