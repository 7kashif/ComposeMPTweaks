package com.kashif.composemptweaks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.core.module.Module

@Composable
fun App() {
    MaterialTheme {
//        FluidBottomBar()
        TestComposable()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestComposable() {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        floatingActionButton = {
            Text(
                "Floating Action Button",
                modifier = Modifier.clickable {
                    scope.launch {
                        state.animateScrollToItem(0)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            state = state
        ) {
            stickyHeader {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex
                ) {
                    repeat(7) {
                        Tab(
                            selected = it == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = it
                            }
                        ) {
                            Text(
                                "Item $it",
                                modifier = Modifier.padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            items(300) {
                Text(
                    "Item $it",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

expect fun platformModule(): Module

expect fun currentPlatform(): String

@Composable
expect fun getScreenDimension(): Pair<Int, Int>
