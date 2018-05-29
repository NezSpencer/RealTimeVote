package com.nezspencer.realtimevote

data class Election(var electoralSeat : String, var contestants : ArrayList<Contestant>) {
    constructor(): this("", ArrayList<Contestant>())
}