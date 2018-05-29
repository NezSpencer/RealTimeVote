package com.nezspencer.realtimevote.Dashboard

interface DashboardContract {

    interface Presenter {
        fun createNewElection()
        fun voteInExistingElection()
    }

    interface View {
        fun onCreateNewElectionClicked()

        fun onVoteInExistingElectionClicked()
    }
}