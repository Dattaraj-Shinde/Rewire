package com.rewire.app.ui.interventions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues

@Composable
fun InterventionsScreen(innerPadding: PaddingValues) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF14141E))
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Interventions",
            color = Color.White
        )
    }
}