package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository

class VoteContestantUsecase(private val repository: ElectionRepository, private val contestantId: String,
                            val email: String, val electionId: String) {

    suspend operator fun invoke(): Boolean {
        return if (contestantId.isEmpty() || electionId.isEmpty() || email.isEmpty()) false
        else repository.vote(email, contestantId, electionId)
    }
}

