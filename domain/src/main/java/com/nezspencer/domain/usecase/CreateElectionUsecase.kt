package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election
import java.util.*

class CreateElectionUsecase(private val electionRepo: ElectionRepository,
                            private val election: Election,
                            private val email: String) {

    suspend operator fun invoke(): Boolean {
        if (email.isEmpty()
                || email.contains(".")
                || election.contestants.size == 0
                || election.createdBy.isEmpty() || election.endDate <= Date().time
                || election.creatorEmail.isEmpty()
                || election.electoralSeat.isEmpty()
                || election.title.isEmpty())
            return false
        return electionRepo.createElection(email, election)
    }
}