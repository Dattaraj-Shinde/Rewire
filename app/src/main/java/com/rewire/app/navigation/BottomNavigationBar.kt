package com.rewire.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    val backStack by navController.currentBackStackEntryAsState()

    val currentRoute = backStack?.destination?.route

    val items = listOf(

        BottomNavItem(
            title = "Dashboard",
            route = Routes.Dashboard.route,
            icon = Icons.Rounded.Home
        ),

        BottomNavItem(
            title = "Analytics",
            route = Routes.Analytics.route,
            icon = Icons.Rounded.Analytics
        ),

        BottomNavItem(
            title = "Practice",
            route = Routes.Interventions.route,
            icon = Icons.Rounded.Psychology
        ),

        BottomNavItem(
            title = "Settings",
            route = Routes.Settings.route,
            icon = Icons.Rounded.Settings
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp
            ),

        shape = RoundedCornerShape(30.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1B26)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                ),

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { item ->

                val selected = currentRoute == item.route

                if (selected) {

                    Row(
                        modifier = Modifier
                            .background(
                                Color(0xFF18C6F4),
                                RoundedCornerShape(50.dp)
                            )
                            .clickable {

                                if (currentRoute != item.route) {

                                    navController.navigate(item.route) {

                                        launchSingleTop = true

                                        restoreState = true

                                        popUpTo(
                                            navController.graph.startDestinationId
                                        ) {

                                            saveState = true
                                        }
                                    }
                                }
                            }
                            .padding(
                                horizontal = 18.dp,
                                vertical = 12.dp
                            ),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = item.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                } else {

                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clickable {

                                navController.navigate(item.route) {

                                    launchSingleTop = true

                                    restoreState = true

                                    popUpTo(
                                        navController.graph.startDestinationId
                                    ) {

                                        saveState = true
                                    }
                                }
                            },

                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color(0xFF80869B),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}