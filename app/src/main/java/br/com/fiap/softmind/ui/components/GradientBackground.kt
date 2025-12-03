package br.com.fiap.softmind.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import br.com.fiap.softmind.ui.theme.GradientEndBlue
import br.com.fiap.softmind.ui.theme.GradientStartGreen

@Composable
fun GradientBackground(
    content: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(

                        GradientStartGreen,
                        GradientEndBlue
                    )
                )
            )
    ) {

        content()
    }
}