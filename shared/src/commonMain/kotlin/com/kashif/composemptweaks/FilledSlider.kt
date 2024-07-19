package com.kashif.composemptweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FilledSlider(
    height: Dp = 24.dp,
    rangeFrom: Float = 0f,
    rangeTo: Float = 100f,
    onValueChange: (Float) -> Unit
) {
    val (value, setValue) = remember {
        mutableStateOf(rangeFrom)
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            Canvas(
                modifier = Modifier
                    .clip(RoundedCornerShape(height))
                    .height(height)
                    .fillMaxWidth()
            ) {
                drawIntoCanvas {
                    drawRoundRect(
                        color = Color.Gray,
                        cornerRadius = CornerRadius(this.size.height, this.size.height),
                        size = Size(
                            width = if (value == 0f) 0f else this.size.width * (value / rangeTo),
                            height = this.size.height
                        )
                    )
                }
            }
            Slider(
                value = value,
                onValueChange = {
                    setValue(it)
                    onValueChange(it)
                },
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(height))
                    .border(1.dp, Color.Gray, RoundedCornerShape(height))
                    .height(height),
                colors = SliderDefaults.colors(
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent,
                    thumbColor = Color.Black
                ),
                valueRange = rangeFrom.rangeTo(rangeTo),
            )
        }

    }
}