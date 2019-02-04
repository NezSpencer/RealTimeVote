package com.nezspencer.domain.entity

data class Election(var electoralSeat: String,
                    val contestants: MutableList<Contestant>,
                    val endDate: Long,
                    var id: String) {
    constructor() : this("", mutableListOf<Contestant>(), 12, "")
}