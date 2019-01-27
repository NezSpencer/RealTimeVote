package com.nezspencer.realtimevote.model

data class Contestant(var id: String = "", var publicName: String = "") {
    constructor() : this("", "")
}