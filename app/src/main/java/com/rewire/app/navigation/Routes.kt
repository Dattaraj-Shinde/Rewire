package com.rewire.app.navigation

sealed class Routes(
    val route: String
) {

    object Dashboard : Routes("dashboard")

    object Analytics : Routes("analytics")

    object Interventions : Routes("interventions")

    object Settings : Routes("settings")
}