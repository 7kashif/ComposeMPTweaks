package com.kashif.composemptweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade


val listOfImages = listOf(
    "https://images.unsplash.com/photo-1537420327992-d6e192287183?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwxfHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1454789548928-9efd52dc4031?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwyfHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1505506874110-6a7a69069a08?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwzfHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1464802686167-b939a6910659?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw0fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1610296669228-602fa827fc1f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw1fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1608178398319-48f814d0750c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw2fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw3fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1528722828814-77b9b83aafb2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw4fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1516339901601-2e1b62dc0c45?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw5fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1506318137071-a8e063b4bec0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwxMHx8c3BhY2V8ZW58MHx8fHwxNjkwMDU3MzY4fDA&ixlib=rb-4.0.3&q=80&w=400"
)


@Composable
fun ClubbedPhotos(
    listOfPhotos: List<String> = listOfImages,
    visiblePhotos: Int = 4, //number of images to be displayed before showing +[NUM] More overlay.
    onClick: () -> Unit = {},
    onMoreClicked: () -> Unit = {}
) {

    require(listOfPhotos.size >= visiblePhotos) {
        "size of listOfPhotos should be greater than or equal to visiblePhotos."
    }

    val chunkedList = remember {
        mutableStateListOf<List<String>>()
    }
    var moreNumber by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = Unit, block = {
        moreNumber = (listOfPhotos.size - visiblePhotos) + 1

        listOfPhotos.subList(0, visiblePhotos).chunked(2).forEach {
            chunkedList.add(it)
        }

    })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chunkedList.forEachIndexed { oIndex, items ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEachIndexed { index, item ->
                    PhotoItem(
                        uri = item,
                        showOverLay = oIndex == chunkedList.size - 1 && index == items.size - 1 && visiblePhotos < listOfPhotos.size,
                        num = moreNumber,
                        modifier = Modifier.weight(1f),
                        onClick = onClick,
                        onMoreClicked = onMoreClicked
                    )
                }
            }
        }
    }
}


/**
 * io.coil-kt:coil-compose:2.2.2
 *
 * add this library for AsyncImage.
 */
@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    uri: String,
    imageHeight: Dp = 150.dp,
    showOverLay: Boolean = false,
    num: Int = 0,
    onClick: () -> Unit = {},
    onMoreClicked: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.height(imageHeight)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current).data(uri).crossfade(true).build(),
            contentDescription = uri,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize().clickable {
                if(showOverLay.not())
                    onClick()
            }
        )
        if (showOverLay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable { onMoreClicked() }
            )
            Text(
                text = "+$num More",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        }
    }
}