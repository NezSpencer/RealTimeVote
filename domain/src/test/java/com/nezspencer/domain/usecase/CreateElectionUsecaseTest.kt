package com.nezspencer.domain.usecase

import com.nezspencer.domain.entity.Election
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CreateElectionUsecaseTest {

    @Test
    fun createUserElection() {
        val reposStub = FakeElectionRepo()
        val testEmail = "nuhiara@gmail.com"
        val userUsecase = CreateElectionUsecase(reposStub, Election(), testEmail)

        runBlocking { assert(userUsecase()) }
    }
}