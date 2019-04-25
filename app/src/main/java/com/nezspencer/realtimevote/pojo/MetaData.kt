package com.nezspencer.realtimevote.pojo

data class MetaData(val electionId: String,
                    val electionTitle: String,
                    val electoralSeat: String,
                    val endDate: Long) {
    constructor() : this("", "", "", 0L)
}