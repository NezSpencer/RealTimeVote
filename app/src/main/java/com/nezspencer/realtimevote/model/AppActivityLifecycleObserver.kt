package com.nezspencer.realtimevote.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.annotation.NonNull

class AppActivityLifecycleObserver(@NonNull val lifecycleListener: LifecycleListener, lifecycle: Lifecycle) :
        LifecycleObserver {
    init {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            lifecycleListener.attachListener()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun attach() {
        lifecycleListener.attachListener()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun detach() {
        lifecycleListener.detachListener()
    }
}