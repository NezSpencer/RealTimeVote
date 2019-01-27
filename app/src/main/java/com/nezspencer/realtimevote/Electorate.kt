package com.nezspencer.realtimevote

data class Electorate(var voterId: String, var isVoted: String) {
    constructor() : this("", "no")
}