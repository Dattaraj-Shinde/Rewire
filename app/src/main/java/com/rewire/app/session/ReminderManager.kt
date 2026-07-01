package com.rewire.app.session

import android.os.Handler
import android.os.Looper
import android.util.Log

object ReminderManager {

    private val handler =
        Handler(
            Looper.getMainLooper()
        )

    fun startReminder(
        delayMillis: Long,
        onReminder: () -> Unit
    ) {

        handler.postDelayed({

            Log.d(
                "REWIRE",
                "Reminder Triggered"
            )

            onReminder()

        }, delayMillis)
    }
}