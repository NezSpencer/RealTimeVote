package com.nezspencer.domain

import com.nezspencer.domain.entity.Election

interface Database {
    suspend fun createElection(email: String, election: Election): Boolean

    fun switchNode(path: String)
}