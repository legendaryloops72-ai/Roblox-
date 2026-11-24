package com.cart3r.robloxstoryai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cart3r.robloxstoryai.ui.RobloxStoryApp
import com.cart3r.robloxstoryai.ui.theme.RobloxStoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RobloxStoryTheme {
                RobloxStoryApp()
            }
        }
    }
}
