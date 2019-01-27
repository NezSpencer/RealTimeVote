package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository

class FetchSubscribedElections(private val electionRepo: ElectionRepository,
                               private val userId: String) {
    //operator fun invoke() = electionRepo.getEligibleElections(userId)
}