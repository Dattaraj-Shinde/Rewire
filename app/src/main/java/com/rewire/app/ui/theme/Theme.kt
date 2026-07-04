package com.rewire.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(

    primary = Primary,
    secondary = Blue,
    tertiary = Purple,

    background = Background,
    surface = Surface,

    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary

)

private val LightColors = lightColorScheme(

    primary = Primary,
    secondary = Blue,
    tertiary = Purple

)

@Composable
fun RewireTheme(

    darkTheme: Boolean = true,

    content: @Composable () -> Unit

) {

    MaterialTheme(

        colorScheme = if (darkTheme)
            DarkColors
        else
            LightColors,

        typography = Typography,

        content = content
    )
}