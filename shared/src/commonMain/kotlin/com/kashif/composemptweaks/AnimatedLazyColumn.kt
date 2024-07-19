package com.example.composetweaks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val testList = List(10) {
    it
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimatedLazyColumn(
    items: List<Int> = testList
) {
    require(items.isNotEmpty()) {
        "items must not be empty."
    }

    val listState = rememberLazyListState()

    val list = remember {
        mutableStateListOf(items[0])
    }

    LaunchedEffect(key1 = Unit, block = {
        items.forEach {
            delay(130)
            list.add(it)
        }
    })

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = list,
        ) {
            val dismissState = rememberDismissState(
                confirmStateChange = { value ->
                    if(value.ordinal == 1 || value.ordinal == 2)
                        println("dismissState item has been dismissed")
                    true
                }
            )
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            var itemAppeared by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                itemAppeared = true
            }
            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                    )
                ),
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "<- Swipe to delete",
                            )
                        }
                    },
                    dismissContent = {
                        ListItem(index = it)
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {
                        FractionalThreshold(0.5f)
                    }
                )
            }
        }
    }
}

@Composable
fun ListItem(
    index: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Item #$index",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
    }
}