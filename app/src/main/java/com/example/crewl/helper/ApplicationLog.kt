package com.example.crewl.helper

import android.util.Log

object ApplicationLog {
    private const val TAG: String = "Application.Tag"

    fun setLog(message: String) {
        Log.i(TAG, message)
    }
}