package com.example.crewl.core

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
    companion object {
        private lateinit var applicationContext: Context
        @JvmStatic
        fun getContext(): Context = applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        BaseApplication.applicationContext = this.applicationContext
    }
}