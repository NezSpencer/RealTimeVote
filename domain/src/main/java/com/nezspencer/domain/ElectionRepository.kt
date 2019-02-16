package com.nezspencer.domain

import com.nezspencer.domain.entity.Election

interface ElectionRepository {
    suspend fun checkIfUserHasVoted(electionId: String, email: String): Boolean

    suspend fun createElection(email: String, election: Election): Boolean

    suspend fun vote(email: String, contestantId: String, electionId: String): Boolean


}