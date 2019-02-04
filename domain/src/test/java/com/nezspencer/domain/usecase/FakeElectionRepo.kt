package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class FakeElectionRepo : ElectionRepository {
    val database = mutableMapOf<String, MutableList<Election>>()
    override fun listenToDifferentNode(path: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun createElection(email: String, election: Election): Boolean {
        val key = "subscribed/$email"
        val list = database.getOrDefault(key, mutableListOf())
        list.add(election)
        database[key] = list

        return database.containsKey(key)
    }
}