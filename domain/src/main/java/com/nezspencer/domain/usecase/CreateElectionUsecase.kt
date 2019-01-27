package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class CreateElectionUsecase(private val electionRepo: ElectionRepository,
                            private val elections: List<Election>,
                            private val email: String) {

    operator fun invoke() = electionRepo.createElection(email, elections)
}