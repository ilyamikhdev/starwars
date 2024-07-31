package com.example.starwars.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.starwars.ui.theme.Color00000000
import com.example.starwars.ui.theme.ColorFF000000
import com.example.starwars.ui.theme.ColorFF1E1266

@Composable
fun CustomSurface(block: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorFF1E1266,
                        ColorFF000000
                    )
                )
            ),
        color = Color00000000
    ) {
        block()
    }
}

@Preview
@Composable
fun CustomSurfacePreview() {
    CustomSurface {}
}
