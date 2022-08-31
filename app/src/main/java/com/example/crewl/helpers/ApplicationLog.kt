package com.example.crewl.helpers

import android.util.Log

object ApplicationLog {
    private const val TAG: String = "Application.Tag"

    fun setLog(message: String) {
        Log.i(TAG, message)
    }
}