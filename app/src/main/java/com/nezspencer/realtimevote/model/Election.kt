package com.nezspencer.realtimevote.model


data class Election(
        val title: String,
        var electoralSeat: String,
        val contestants: MutableList<Contestant>,
        val endDate: Long = 12,
        val creator: String,
        val creatorEmail: String,
        var id: String = "") {
    constructor() : this("", "", mutableListOf<Contestant>(), 0,
            "", "", "")

}