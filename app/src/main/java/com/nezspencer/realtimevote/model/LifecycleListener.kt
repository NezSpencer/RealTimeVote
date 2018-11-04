package com.nezspencer.realtimevote.model

interface LifecycleListener {
    fun attachListener()

    fun detachListener()
}