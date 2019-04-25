package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository
import com.nezspencer.domain.entity.Election

class VoteContestantUsecase(private val repository: ElectionRepository, private val contestantId: String,
                            val email: String, val election: Election) {

    suspend operator fun invoke(): Boolean {
        val electionId = election.id
        val electionTitle = election.title
        val seat = election.electoralSeat
        return if (contestantId.isEmpty() || electionId.isEmpty() || email.isEmpty() ||
                electionTitle.isEmpty() || seat.isEmpty() || election.endDate == 0L) false
        else repository.vote(email, contestantId, electionId, electionTitle, seat, election.endDate)
    }
}

