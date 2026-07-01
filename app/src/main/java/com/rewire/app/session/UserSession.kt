package com.rewire.app.session

data class UserSession(
    val appPackage: String,
    val reason: String,
    val durationMinutes: Int,
    val startTime: Long
)