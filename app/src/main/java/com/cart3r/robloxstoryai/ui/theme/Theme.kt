package com.cart3r.robloxstoryai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StoryColors = darkColorScheme(
    primary = Color(0xFFFF4058),
    onPrimary = Color.White,
    secondary = Color(0xFFFFA6B1),
    background = Color(0xFF09090D),
    surface = Color(0xFF17171F),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun RobloxStoryTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = StoryColors, content = content)
}
