package com.nezspencer.realtimevote

import android.app.Application

class App : Application() {
companion object {
    @JvmStatic lateinit var elections : ArrayList<Election>
}
    override fun onCreate() {
        super.onCreate()
        elections = ArrayList()
    }
}