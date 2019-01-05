package com.nezspencer.realtimevote

import android.content.Context
import android.preference.PreferenceManager

fun getUserEmail(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(AppActivity.KEY_EMAIL, "anonymous@voter.com")

fun String.removeDots() = this.replace(".", ",")