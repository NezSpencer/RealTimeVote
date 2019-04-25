package com.nezspencer.domain.usecase

import com.nezspencer.domain.ElectionRepository

class GetVoteResultListUsecase(private val repository: ElectionRepository) {

    operator fun invoke() {
        repository.getElectionResults()
    }
}