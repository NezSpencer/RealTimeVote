package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class CreateElectionUsecase(private val electionRepo: ElectionRepository,
                            private val election: Election,
                            private val email: String) {

    suspend operator fun invoke() = electionRepo.createElection(email, election)
}