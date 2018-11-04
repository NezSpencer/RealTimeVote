package com.nezspencer.realtimevote.pojo

data class Vote(val voterId: String,
                val voted: String,
                val electoralSeatId: String,
                val time: Long)