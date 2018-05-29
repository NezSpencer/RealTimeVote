package com.nezspencer.realtimevote

data class Contestant(var name: String, var isVoted : Boolean){
    constructor() : this("",false)
}