package com.nezspencer.domain

import com.nezspencer.domain.entity.Election

interface Database {
    suspend fun createElection(email: String, election: Election): Boolean

    suspend fun hasUserVoted(electionId: String, email: String): Boolean

    suspend fun vote(email: String, contestantId: String, electionId: String): Boolean
}