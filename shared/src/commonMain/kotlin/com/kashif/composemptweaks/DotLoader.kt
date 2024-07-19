package com.kashif.composemptweaks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun DotLoader(
    dotsCount: Int = 4,
    dotColor: Color = Color.DarkGray,
    dotSize: Float = 8f,
    spacingFactor: Float = 3f,
    jumpFactor: Float = 2f
) {
    val starts = remember {
        mutableStateListOf(*Array(dotsCount) { false })
    }
    val offSets = Array(dotsCount) { index ->
        animateFloatAsState(targetValue = if (starts[index]) dotSize * jumpFactor else 0f,
            label = "dot$index/animation",
            animationSpec = tween(600),
            finishedListener = {
                starts[index] = !starts[index]
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        repeat(dotsCount) {
            starts[it] = true
            delay(100)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.wrapContentSize(), onDraw = {
            repeat(dotsCount) {
                drawCircle(
                    color = dotColor,
                    radius = dotSize,
                    center = Offset(
                        dotSize + it * dotSize * spacingFactor,
                        dotSize - offSets[it].value
                    )
                )
            }
        })
    }
}

