package com.nezspencer.domain.entity

import java.util.*

data class Election(var electoralSeat: String,
                    val contestants: ArrayList<String>,
                    val endDate: Int) {
    constructor() : this("", arrayListOf<String>(), 12)
}