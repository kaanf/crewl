package com.example.crewl.core

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver

class BaseApplication: Application(), LifecycleObserver {
    companion object {
        private lateinit var application: BaseApplication
        @JvmStatic
        fun getContext(): Context = application.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        application = this
    }
}