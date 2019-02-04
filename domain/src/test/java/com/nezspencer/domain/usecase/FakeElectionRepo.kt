package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class FakeElectionRepo : ElectionRepository {
    private val database = mutableMapOf<String, MutableList<Election>>()
    override fun listenToDifferentNode(path: String) {

    }

    override suspend fun createElection(email: String, election: Election): Boolean {
        val key = "subscribed/$email"
        val list = database.getOrDefault(key, mutableListOf())
        list.add(election)
        database[key] = list

        return database.containsKey(key)
    }
}