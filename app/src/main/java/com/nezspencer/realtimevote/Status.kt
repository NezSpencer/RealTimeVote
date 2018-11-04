package com.nezspencer.realtimevote

enum class State {
    LOADED, LOADING, FAILED, IDLE
}

class Status private constructor(val state: State, val msg: String? = null) {

    companion object {
        val Success = Status(State.LOADED)
        val Running = Status(State.LOADING)
        val Refresh = Status(State.IDLE)
        fun error(msg: String) = Status(State.FAILED, msg)
    }

}