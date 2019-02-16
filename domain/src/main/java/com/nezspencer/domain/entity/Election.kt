package com.nezspencer.domain.entity

data class Election(
        var title: String,
        var electoralSeat: String,
        val contestants: MutableList<Contestant>,
        val endDate: Long,
        val createdBy: String,
        val creatorEmail: String,
        var id: String) {
    constructor() : this("", "", mutableListOf<Contestant>(), 12,
            "", "", "")
}