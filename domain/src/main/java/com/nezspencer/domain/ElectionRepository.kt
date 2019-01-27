package com.nezspencer.domain

import com.nezspencer.domain.entity.Election

interface ElectionRepository {
    fun listenToDifferentNode(path: String)

    fun createElection(email: String, election: List<Election>)


}