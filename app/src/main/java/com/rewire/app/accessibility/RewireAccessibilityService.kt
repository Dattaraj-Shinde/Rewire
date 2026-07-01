package com.rewire.app.accessibility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.rewire.app.overlay.OverlayManager

class RewireAccessibilityService : AccessibilityService() {

    private lateinit var overlayManager: OverlayManager

    private val distractingApps = setOf(
        "com.android.chrome",
        "com.instagram.android",
        "com.google.android.youtube",
        "com.snapchat.android",
        "com.facebook.katana",
        "com.reddit.frontpage"
    )

    override fun onServiceConnected() {

        super.onServiceConnected()

        overlayManager = OverlayManager(this)

        Log.d(
            "REWIRE",
            "SERVICE CONNECTED"
        )
    }

    override fun onAccessibilityEvent(
        event: AccessibilityEvent?
    ) {

        if (
            event?.eventType !=
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        ) {
            return
        }

        val packageName =
            event.packageName?.toString()
                ?: return

        val ignoredPackages = setOf(
            "com.rewire.app",
            "com.android.systemui"
        )

        if (packageName in ignoredPackages) {
            return
        }

        Log.d(
            "REWIRE",
            "Opened: $packageName"
        )

        if (
            packageName in distractingApps
        ) {

            overlayManager.showOverlay(
                packageName
            )
        }
    }

    override fun onInterrupt() {

        Log.d(
            "REWIRE",
            "Accessibility Service Interrupted"
        )
    }
}