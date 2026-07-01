package com.rewire.app.session

import android.util.Log

object SessionManager {

    private var currentSession: UserSession? = null

    fun startSession(session: UserSession) {
        currentSession = session

        Log.d(
            "REWIRE",
            "Session Started: $session"
        )
    }

    fun getCurrentSession(): UserSession? {
        return currentSession
    }

    fun endSession() {
        currentSession = null
    }
}