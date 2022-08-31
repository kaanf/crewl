package com.example.crewl.helpers

import android.os.CountDownTimer

class CrewlTimer {
    interface TimerCallback {
        fun finish()
    }

    private var callback: TimerCallback? = null
    private var timer: CountDownTimer? = null

    companion object {
        fun delay(
            seconds: Double,
            callback: TimerCallback
        ): CrewlTimer {
            val helper = CrewlTimer()
            helper.callback = callback
            helper.timer = object : CountDownTimer(
                (seconds * 1000).toInt().toLong(),
                (seconds * 1000).toInt().toLong()
            ) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    callback.finish()
                    helper.callback = null
                    helper.timer = null
                }
            }

            helper.timer?.start()

            return helper
        }
    }

    fun execute() {
        callback?.let { callback ->
            timer?.let { timer ->
                timer.cancel()
                this@CrewlTimer.timer = null
            }

            callback.finish()
            this@CrewlTimer.callback = null
        }
    }

    fun cancel() {
        timer?.cancel()
    }
}