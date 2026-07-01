package com.rewire.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rewire.app.navigation.AppNavigation
import com.rewire.app.ui.theme.RewireTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            RewireTheme {

                AppNavigation()

            }
        }
    }
}