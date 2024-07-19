package com.kashif.composemptweaks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun RatingBarEditable(
    rating: Float = 3f,
    maxRating: Int = 5,
    starSize: Dp = 32.dp,
    isEditable: Boolean = true,
    isDraggable: Boolean = true, //whether or not be editable by dragging
    roundOffToWholeNumber: Boolean = false, //will round off to whole star instead of float point
    ratingColor: Color = Color.Gray,
    onValueChange: (Float) -> Unit
) {
    var ratingChange by remember(key1 = rating) {
        mutableStateOf(rating)
    }

    Row(
        modifier = Modifier.pointerInput(Unit) {
            if (isEditable && isDraggable) {
                detectHorizontalDragGestures { change, _ ->
                    //converting the value in range of 0 to row's width to the range of 0 to maxRating
                    val cc = change.position.x.convertToRange(
                        oldRangeStart = 0f,
                        oldRangeEnd = size.width.toFloat(),
                        newRangeStart = 0f,
                        newRangeEnd = maxRating.toFloat()
                    )

                    ratingChange =
                        cc.coerceIn(maximumValue = maxRating.toFloat(), minimumValue = 0f)

                    onValueChange(ratingChange)

                }
            }
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(maxRating) { index ->
            Box(
                modifier = Modifier
                    .clip(StarShape())
                    .size(starSize)
                    .border(1.dp, ratingColor, StarShape())
            ) {
                Canvas(
                    modifier = Modifier
                        .clip(StarShape())
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            if (isEditable) {
                                detectTapGestures {
                                    ratingChange = if (roundOffToWholeNumber)
                                        (index + 1).toFloat()
                                    else
                                        index.toFloat() + (it.x / size.width)

                                    onValueChange(ratingChange)
                                }
                            }
                        }
                ) {
                    drawIntoCanvas {
                        drawRect(
                            color = ratingColor,
                            size = Size(
                                width =
                                if (index < ratingChange.toInt())
                                    this.size.width
                                else if (index == ratingChange.toInt())
                                    this.size.width * (ratingChange - ratingChange.toInt())
                                else
                                    0f,
                                height = this.size.height
                            )
                        )
                    }
                }
            }
        }
    }
}


/**
 * Round a value from a range to different range
 *
 * e.g, rounding 50 in range of 0 to 100 to the range of 0 to 10 (which will result 5).
 */
fun Float.convertToRange(
    oldRangeStart: Float,
    oldRangeEnd: Float,
    newRangeStart: Float,
    newRangeEnd: Float
) =
    newRangeStart + (this - oldRangeStart) * (newRangeEnd - newRangeStart) / (oldRangeEnd - oldRangeStart)


class StarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val path = Path()

        // Define the points of the star shape
        val outerRadius = size.minDimension / 2.0f
        val innerRadius = outerRadius / 2.5f
        val numPoints = 5
        val angleStep = 360.0f / numPoints

        var currentAngle = -90.0f
        for (i in 0 until numPoints * 2) {
            val radius = if (i % 2 == 0) outerRadius else innerRadius
            val x =
                size.width / 2.0f + radius * kotlin.math.cos(Math.toRadians(currentAngle.toDouble()))
                    .toFloat()
            val y =
                size.height / 2.0f + radius * kotlin.math.sin(Math.toRadians(currentAngle.toDouble()))
                    .toFloat()
            val point = Offset(x, y)

            if (i == 0) {
                path.moveTo(point.x, point.y)
            } else {
                path.lineTo(point.x, point.y)
            }

            currentAngle += angleStep
        }

        op(path1 = path, path2 = path, operation = PathOperation.Union)
    })
}


