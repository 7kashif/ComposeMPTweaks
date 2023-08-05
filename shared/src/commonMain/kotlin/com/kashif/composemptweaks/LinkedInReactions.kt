package com.kashif.composemptweaks

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.kashif.composemptweaks.domain.DrawableHelper
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val reactions = listOf(
    DrawableHelper.LIKE,
    DrawableHelper.HEART,
    DrawableHelper.SUPPORT,
    DrawableHelper.CLAP,
    DrawableHelper.CURIOUS,
    DrawableHelper.INSIGHTFUL
)
private val SIZE = 48.dp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LinkedInReaction() {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val animationsToggle = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(reactions.size) {
                add(false)
            }
        }
    }

    LaunchedEffect(key1 = startAnimation) {
        if (startAnimation) {
            repeat(reactions.size) { index ->
                animationsToggle[index] = true
                delay(130)
            }
        } else {
            repeat(reactions.size) { index ->
                animationsToggle[index] = false
                delay(100)
            }
        }
    }

    val xOffSets = getListOfXAnimations(animationToggles = animationsToggle)
    val yOffSets = getListOfYAnimations(animationToggles = animationsToggle)
    val sizes = getSizes(animationToggles = animationsToggle)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            reactions.forEachIndexed { index, item ->
                Image(
                    modifier = Modifier
                        .offset(x = xOffSets[index].value, y = yOffSets[index].value)
                        .size(sizes[index].value),
                    painter = painterResource(res = item),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Image(
                modifier = Modifier
                    .size(SIZE)
                    .clip(CircleShape)
                    .clickable {
                        startAnimation = !startAnimation
                    },
                painter = painterResource(res = DrawableHelper.LIKE),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }

}


@Composable
fun getAnimationsX(startAnimation: Boolean, count: Int) = animateDpAsState(
    targetValue = if (startAnimation) (count * SIZE) + (count * 8.dp) else 16.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun getAnimationsY(startAnimation: Boolean) = animateDpAsState(
    targetValue = if (startAnimation) -(SIZE + 16.dp) else 16.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun getAnimatedSize(startAnimation: Boolean) = animateDpAsState(
    targetValue = if (startAnimation) SIZE else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun getListOfXAnimations(animationToggles: List<Boolean>) = List(reactions.size) {
    getAnimationsX(startAnimation = animationToggles[it], count = it)
}

@Composable
fun getListOfYAnimations(animationToggles: List<Boolean>) = List(reactions.size) {
    getAnimationsY(startAnimation = animationToggles[it])
}

@Composable
fun getSizes(animationToggles: List<Boolean>) = List(reactions.size) {
    getAnimatedSize(startAnimation = animationToggles[it])
}