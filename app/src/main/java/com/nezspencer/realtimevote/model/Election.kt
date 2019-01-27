package com.nezspencer.realtimevote.model


import java.util.*

data class Election(var electoralSeat: String,
                    val contestants: ArrayList<Contestant>,
                    val endDate: Int,
                    var id: String) {
    constructor() : this("", arrayListOf<Contestant>(), 12, "")

}