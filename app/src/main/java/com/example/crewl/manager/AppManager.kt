package com.example.crewl.manager

import android.app.ActivityManager
import android.content.Context
import com.example.crewl.core.BaseApplication

class AppManager {
    companion object {
        var isLowRam = false

        fun start() {
            val manager: ActivityManager = BaseApplication.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
            manager.getMemoryInfo(memoryInfo)

            isLowRam = memoryInfo.totalMem < 4000000000L
        }
    }
}