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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

val BostonBlue = Color(0xFF3A98B9)

private const val DELTA = 0.7f

@Composable
fun WaterDrop() {
    var screenHeight by remember {
        mutableStateOf(0f)
    }
    var screenWidth by remember {
        mutableStateOf(0f)
    }
    var draggedOffSet by remember {
        mutableStateOf(Offset(-1f,-1f))
    }
     var starOffSet by remember {
        mutableStateOf(Offset(0f,0f))
    }
    var startDrop by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(startDrop) {
        if(startDrop) {
            var y = draggedOffSet.y
            while ( y < screenHeight * DELTA) {
                y++
                draggedOffSet = Offset(draggedOffSet.x, y)
                delay(1)
            }
            draggedOffSet = Offset(-1f,-1f)
            startDrop = false
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        startDrop = true
                    },
                    onDragStart = {
                        startDrop = false
                        starOffSet = it
                    }
                ) { change, _ ->
                    if(change.position.y < screenHeight * DELTA && starOffSet.y > screenHeight * DELTA) {
                        draggedOffSet = change.position
                    }
                }
            }
            .graphicsLayer {
                screenHeight = size.height
                screenWidth = size.width
            }
    ) {
        val height = size.height
        val width = size.width
        val path = Path().apply {
            moveTo(0f, height * DELTA)
            lineTo(width, height * DELTA)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }


        drawPath(
            path = path,
            color = BostonBlue,
            style = Fill
        )

        if(draggedOffSet.x > -1f) {
            val dragPath = Path().apply {
                moveTo(draggedOffSet.x, draggedOffSet.y)
                lineTo(draggedOffSet.x + 340f, height)
                lineTo(draggedOffSet.x - 340f, height)
                close()
            }


            drawPath(
                path = dragPath,
                color = BostonBlue,
                style = Fill
            )
        }

    }

}