package com.rewire.app.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.rewire.app.R
import com.rewire.app.session.ReminderManager
import com.rewire.app.session.SessionManager
import com.rewire.app.session.UserSession

class OverlayManager(
    private val context: Context
) {

    private var overlayView: View? = null

    fun showOverlay(
        packageName: String
    ) {

        if (overlayView != null) return

        val windowManager =
            context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager

        overlayView =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.intent_overlay,
                    null
                )

        val startButton =
            overlayView!!.findViewById<Button>(
                R.id.startButton
            )

        val skipButton =
            overlayView!!.findViewById<Button>(
                R.id.skipButton
            )

        val reasonGroup =
            overlayView!!.findViewById<RadioGroup>(
                R.id.reasonGroup
            )

        val timeGroup =
            overlayView!!.findViewById<RadioGroup>(
                R.id.timeGroup
            )

        startButton.setOnClickListener {

            val reason =
                when (reasonGroup.checkedRadioButtonId) {

                    R.id.replyOption ->
                        "Reply Messages"

                    R.id.workOption ->
                        "Work / Study"

                    R.id.browseOption ->
                        "Browse Intentionally"

                    else ->
                        "Unknown"
                }

            val duration =
                when (timeGroup.checkedRadioButtonId) {

                    R.id.fiveMin -> 5

                    R.id.tenMin -> 10

                    R.id.fifteenMin -> 15

                    else -> 5
                }

            val session =
                UserSession(
                    appPackage = packageName,
                    reason = reason,
                    durationMinutes = duration,
                    startTime = System.currentTimeMillis()
                )

            SessionManager.startSession(
                session
            )

            ReminderManager.startReminder(
                15000
            ) {

                showReminderOverlay()
            }

            removeOverlay()
        }

        skipButton.setOnClickListener {

            removeOverlay()
        }

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

        params.gravity = Gravity.CENTER

        windowManager.addView(
            overlayView,
            params
        )
    }

    fun showReminderOverlay() {

        if (overlayView != null) return

        val session =
            SessionManager.getCurrentSession()
                ?: return

        val windowManager =
            context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager

        val reminderView =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.reminder_overlay,
                    null
                )

        val sessionInfo =
            reminderView.findViewById<TextView>(
                R.id.sessionInfo
            )

        val continueButton =
            reminderView.findViewById<Button>(
                R.id.continueButton
            )

        val exitButton =
            reminderView.findViewById<Button>(
                R.id.exitButton
            )

        sessionInfo.text =
            """
Reason: ${session.reason}

Planned Time: ${session.durationMinutes} minutes

App: ${session.appPackage}
            """.trimIndent()

        overlayView = reminderView

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

        params.gravity = Gravity.CENTER

        continueButton.setOnClickListener {

            removeOverlay()

            showLevelOneIntervention()
        }

        exitButton.setOnClickListener {

            SessionManager.endSession()

            removeOverlay()
        }

        windowManager.addView(
            overlayView,
            params
        )
    }

    fun showLevelOneIntervention() {

        if (overlayView != null) return

        val windowManager =
            context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager

        val interventionView =
            LayoutInflater.from(context)
                .inflate(
                    R.layout.intervention_overlay,
                    null
                )

        val counterText =
            interventionView.findViewById<TextView>(
                R.id.counterText
            )

        val tapButton =
            interventionView.findViewById<Button>(
                R.id.tapButton
            )

        var count = 0

        tapButton.setOnClickListener {

            count++

            counterText.text =
                "$count / 10"

            if (count >= 10) {

                removeOverlay()
            }
        }

        overlayView = interventionView

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

        params.gravity = Gravity.CENTER

        windowManager.addView(
            overlayView,
            params
        )
    }

    fun removeOverlay() {

        val windowManager =
            context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager

        overlayView?.let {

            windowManager.removeView(it)

            overlayView = null
        }
    }
}