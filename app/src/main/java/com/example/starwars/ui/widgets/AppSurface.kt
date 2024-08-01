package com.example.starwars.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.starwars.ui.theme.ColorBlack
import com.example.starwars.ui.theme.ColorFF1E1266
import com.example.starwars.ui.theme.ColorTransparent

@Composable
fun AppSurface(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorFF1E1266,
                        ColorBlack
                    )
                )
            ),
        color = ColorTransparent
    ) {
        content()
    }
}

@Preview
@Composable
fun CustomSurfacePreview() {
    AppSurface {}
}
