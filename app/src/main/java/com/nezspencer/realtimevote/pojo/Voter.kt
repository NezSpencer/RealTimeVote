package com.nezspencer.realtimevote.pojo

data class Voter(val name: String,
                 val uniqueId: String,
                 val isContesting: Boolean)