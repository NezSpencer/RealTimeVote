package com.nezspencer.realtimevote

import android.content.Context
import android.preference.PreferenceManager
import android.text.format.DateUtils
import com.nezspencer.realtimevote.model.Contestant
import com.nezspencer.realtimevote.model.Election

fun getUserEmail(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(AppActivity.KEY_EMAIL, "anonymous@voter.com")

fun getUserName(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(AppActivity.KEY_USERNAME, "")

fun String.removeDots() = this.replace(".", ",")
fun Election.toDomainElectionModel() = com.nezspencer.domain.entity.Election(this.title,
        this.electoralSeat,
        contestants.toDomainContestantList(), this.endDate, this.creator, this.creatorEmail, this.id)
fun ArrayList<Election>.toDomainElectionModel(): ArrayList<com.nezspencer.domain.entity.Election> {
    val result: ArrayList<com.nezspencer.domain.entity.Election> = ArrayList()
    for (election in this) {
        result.add(election.toDomainElectionModel())
    }

    return result
}

fun Contestant.toDomainContestantModel() = com.nezspencer.domain.entity.Contestant(id, publicName)

fun MutableList<Contestant>.toDomainContestantList(): MutableList<com.nezspencer.domain.entity.Contestant> {
    val result = ArrayList<com.nezspencer.domain.entity.Contestant>()
    for (contestant in this)
        result.add(contestant.toDomainContestantModel())
    return result
}

fun getSubscribedPath(email: String, electionKey: String = "") =
        "/subscribed/${email.removeDots()}/$electionKey"

fun getAllElectionsPath(electionKey: String) = "/all/$electionKey"

fun getContestantVotePath(electionId: String, contestantId: String, voteKey: String) = "/results/$electionId/votes/$contestantId/$voteKey"

fun getVoteStatusPath(electionId: String, email: String) = "Voted/$electionId/$email"

fun getAppPrettyDate(context: Context, date: Long) = DateUtils.getRelativeDateTimeString(context,
        date, 60000, 259200000, DateUtils.FORMAT_ABBREV_ALL)

fun getVoteExtraPath(electionId: String) = "/results/$electionId/extras"

fun getResultListPath() = "results/"