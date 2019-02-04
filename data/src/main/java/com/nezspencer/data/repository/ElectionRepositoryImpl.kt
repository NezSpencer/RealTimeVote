package com.nezspencer.data.repository

import com.nezspencer.domain.Database
import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class ElectionRepositoryImpl(private val database: Database) : ElectionRepository {
    override fun listenToDifferentNode(path: String) {
        database.switchNode(path)
    }

    override suspend fun createElection(email: String, election: Election): Boolean {
        return database.createElection(email, election)
    }


}