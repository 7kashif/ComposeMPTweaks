package com.kashif.composemptweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

val BostonBlue = Color(0xFF3A98B9)

@Composable
fun BouncingBallAnimation() {

    var width by remember {
        mutableStateOf(0f)
    }
    var height by remember {
        mutableStateOf(0f)
    }

    var startDrop by remember {
        mutableStateOf(false)
    }

    var x by remember {
        mutableStateOf(0f)
    }

    var y by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(Unit) {
        delay(50)
        x = width / 2f
        y = height
    }

    LaunchedEffect(startDrop) {
        if (startDrop) {
            while (y < height) {
                y++
                delay(1)
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                startDrop = true
            },
            onDragStart = {
                startDrop = false
            }
        ) { change, _ ->
            if (change.position.x > 30f && change.position.x < width && change.position.y > 30f && change.position.y < height)
                if ((change.position.x > x - 30f && change.position.x < x + 30f) && (change.position.y > y - 30f && change.position.y < y + 30f)) {
                    x = change.position.x
                    y = change.position.y
                }
        }
    }.graphicsLayer {
        width = size.width - 30f
        height = size.height - 30f
    }) {
        drawCircle(
            color = BostonBlue,
            center = Offset(x,y),
            radius = 30f
        )
    }

}

