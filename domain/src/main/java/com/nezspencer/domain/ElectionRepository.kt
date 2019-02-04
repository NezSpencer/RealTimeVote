package com.nezspencer.domain

import com.nezspencer.domain.entity.Election

interface ElectionRepository {
    fun listenToDifferentNode(path: String)

    suspend fun createElection(email: String, election: Election): Boolean


}