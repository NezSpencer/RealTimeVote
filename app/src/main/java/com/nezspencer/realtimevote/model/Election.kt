package com.nezspencer.realtimevote.model


data class Election(var electoralSeat: String,
                    val contestants: MutableList<Contestant>,
                    val endDate: Long = 12,
                    var id: String = "") {
    constructor() : this("", mutableListOf<Contestant>(), 12, "")

}