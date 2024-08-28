package com.kashif.composemptweaks

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.kashif.composemptweaks.domain.DrawableHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.math.abs

private const val DELAY = 500

@Composable
fun CardSwipeStack(
    modifier: Modifier = Modifier
) {
    val itemsList = remember {
        mutableStateListOf(*Array(10) { "Item ${it + 1}" })
    }

    var (swipeProgress, setSwipeProgress) = remember {
        mutableStateOf(0f)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (itemsList.isEmpty()) {
            Text(
                text = "You are all set!!! 👍",
                style = MaterialTheme.typography.h6
            )
        }

        itemsList.forEachIndexed { index, item ->
            SwipeToMarkCard(
                modifier = modifier.padding(if (index == itemsList.lastIndex) 0.dp else 24.dp * (1 - swipeProgress)),
                onSwiped = {
                    swipeProgress = 1f
                    itemsList.remove(item)
                },
                content = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.h6
                    )
                },
                onSwipe = setSwipeProgress
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SwipeToMarkCard(
    modifier: Modifier = Modifier,
    onSwiped: () -> Unit,
    onSwipe: (swipeProgress: Float) -> Unit,
    content: @Composable () -> Unit
) {
    var cardOffSet by remember {
        mutableStateOf(DpOffset(0.dp, 0.dp))
    }

    var animateCardBackTo0 by remember {
        mutableStateOf(false)
    }

    var overLayColor by remember {
        mutableStateOf(RED)
    }

    var swipeProgress by remember {
        mutableStateOf(0f)
    }

    val animatedSwipeProgress by animateFloatAsState(
        targetValue = swipeProgress,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animatedRedAlha"
    )

    val animatedOffsetX by animateDpAsState(
        targetValue = cardOffSet.x,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animateOffsetX"
    )

    val animatedOffsetY by animateDpAsState(
        targetValue = cardOffSet.y,
        animationSpec = if (animateCardBackTo0) tween(DELAY) else tween(0),
        label = "animateOffsetY"
    )

    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .offset(animatedOffsetX, animatedOffsetY)
            .rotate(animatedSwipeProgress * if (overLayColor == RED) -15f else -15f)
            .fillMaxSize()
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            if (abs(swipeProgress) >= 1f) {
                                cardOffSet = DpOffset(
                                    cardOffSet.x + if (cardOffSet.x > 0.dp) 300.dp else (-300).dp,
                                    cardOffSet.y
                                )
                                animateCardBackTo0 = true
                                delay(DELAY.toLong())
                                onSwiped()
                            } else {
                                cardOffSet = DpOffset(0.dp, 0.dp)
                                animateCardBackTo0 = true
                                swipeProgress = 0f
                                delay(DELAY.toLong())
                                animateCardBackTo0 = false
                            }
                        }
                    }
                ) { _, dragAmount ->
                    scope.launch {
                        val offSetX = cardOffSet.x + dragAmount.x.toDp()
                        val offSetY = cardOffSet.y + dragAmount.y.toDp()

                        swipeProgress = lerp(0f, 1f, offSetX.value / 200).coerceIn(-1f, 1f)

                        overLayColor = if (animatedSwipeProgress > 0)
                            RED
                        else
                            Green

                        onSwipe(abs(swipeProgress))
                        cardOffSet = DpOffset(offSetX, offSetY)
                    }
                }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        backgroundColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overLayColor.copy(alpha = abs(animatedSwipeProgress)))
            )

            Column(
                modifier = Modifier
                    .align(if (overLayColor == RED) Alignment.TopStart else Alignment.TopEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (abs(animatedSwipeProgress) == 1f) Color.White else Color.Transparent,
                        )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        progress = abs(animatedSwipeProgress),
                        strokeWidth = 4.dp,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(resource = if (overLayColor == RED) DrawableHelper.IC_EYE else DrawableHelper.IC_CHECK_BIG),
                        contentDescription = "",
                        tint = if (abs(animatedSwipeProgress) == 1f) Color.Black else Color.White,
                        modifier = Modifier.alpha(abs(animatedSwipeProgress))
                    )
                }
                Text(
                    text = if (overLayColor == RED) "Mark as Unread" else "Mark as Read",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.alpha(abs(animatedSwipeProgress)),
                    color = Color.White
                )
            }
        }
    }
}