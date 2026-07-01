package com.rewire.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rewire.app.ui.analytics.AnalyticsScreen
import com.rewire.app.ui.dashboard.DashboardScreen
import com.rewire.app.ui.interventions.InterventionsScreen
import com.rewire.app.ui.settings.SettingsScreen

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    Scaffold(

        containerColor = Color(0xFF14141E),

        bottomBar = {

            BottomNavigationBar(
                navController
            )
        }

    ) { innerPadding ->

        NavHost(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF14141E)),

            navController = navController,

            startDestination = Routes.Dashboard.route
        ) {

            composable(
                Routes.Dashboard.route
            ) {

                DashboardScreen(
                    navController = navController,
                    innerPadding = innerPadding
                )
            }

            composable(
                Routes.Analytics.route
            ) {

                AnalyticsScreen(innerPadding = innerPadding)
            }

            composable(
                Routes.Interventions.route
            ) {

                InterventionsScreen(innerPadding = innerPadding)
            }

            composable(
                Routes.Settings.route
            ) {

                SettingsScreen(innerPadding = innerPadding)
            }
        }
    }
}