package com.nezspencer.realtimevote.pojo

data class ResultItem(val electionId: String,
                      val electionTitle: String,
                      val electoralSeat: String,
                      val endDate: Long,
                      val contestantId_Name: String,
                      val voterEmail: String) {
    constructor() : this("", "", "", 0,
            "", "")
}