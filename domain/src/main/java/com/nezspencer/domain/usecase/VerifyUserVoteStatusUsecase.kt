package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository

class VerifyUserVoteStatusUsecase(private val repository: ElectionRepository,
                                  private val electionId: String,
                                  private val email: String) {

    suspend operator fun invoke() = repository.checkIfUserHasVoted(electionId, email)
}