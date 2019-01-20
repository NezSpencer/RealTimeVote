package com.nezspencer.domain

import com.nezspencer.domain.entity.Election

interface Database {
    fun createElection(email: String, election: List<Election>)

    fun switchNode(path: String)
}