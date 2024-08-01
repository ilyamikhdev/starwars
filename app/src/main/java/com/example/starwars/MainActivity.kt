package com.example.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.starwars.ui.navigation.AppNavigation
import com.example.starwars.ui.theme.StarwarsTheme
import com.example.starwars.ui.widgets.AppSurface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarwarsTheme {
                AppSurface {
                    AppNavigation(navHostController = rememberNavController())
                }
            }
        }
    }
}

