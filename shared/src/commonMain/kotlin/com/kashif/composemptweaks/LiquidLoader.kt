package com.kashif.composemptweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos

val BostonBlue = Color(0xFF3A98B9)

@Composable
fun LiquidLoader() {

    var waveOffset by remember {
        mutableStateOf(0f)
    }

    var yPos by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(Unit) {
        while (true) {
            waveOffset += 1f
            delay(3)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .clip(CircleShape)
                .border(2.dp, BostonBlue, CircleShape)
                .size(300.dp)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, _ ->
                        yPos = change.position.y
                    }
                }.graphicsLayer {
                    yPos = size.height
                },
        ) {
            val canvasWidth = size.width

            val wavePath = Path()
            wavePath.moveTo(0f, yPos / 2)

            val amplitude = 40f
            val frequency = 0.0022f

            for (x in 0..canvasWidth.toInt()) {
                val y = yPos / 2 + amplitude * cos(PI * (x - waveOffset) * frequency)
                wavePath.lineTo(x.toFloat(), y.toFloat())
            }

            wavePath.lineTo(canvasWidth, size.height)
            wavePath.lineTo(0f, size.height)
            wavePath.close()

            drawPath(wavePath, color = BostonBlue)
        }

    }

}