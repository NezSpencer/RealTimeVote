package com.nezspencer.realtimevote

import android.content.Context
import android.preference.PreferenceManager
import com.nezspencer.realtimevote.model.Election

fun getUserEmail(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(AppActivity.KEY_EMAIL, "anonymous@voter.com")

fun String.removeDots() = this.replace(".", ",")
fun Election.toDomainElectionModel() = com.nezspencer.domain.entity.Election(this.electoralSeat, this.contestants, this.endDate)
fun ArrayList<Election>.toDomainElectionModel(): ArrayList<com.nezspencer.domain.entity.Election> {
    val result: ArrayList<com.nezspencer.domain.entity.Election> = ArrayList()
    for (election in this) {
        result.add(election.toDomainElectionModel())
    }

    return result
}