package com.kashif.composemptweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif.composemptweaks.domain.ChatViewModel
import com.kashif.composemptweaks.domain.DrawableHelper
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

val sendBg = Color(0xFFF5F7FB)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Chat(
    viewModel: ChatViewModel = koinInject()
) {
    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()
    val (text, setText) = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.initiateChat()
    }

    LaunchedEffect(messages) {
        if (messages.isNotEmpty())
            listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White
                ) {
                    TextField(
                        value = text,
                        onValueChange = setText,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Box(
                                modifier = Modifier.size(40.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(sendBg)
                                    .clickable {
                                        viewModel.sendMessage(text = text)
                                        setText("")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(resource = DrawableHelper.IC_SEND),
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                text = "Enter a message...",
                                color = Color(0xFFEBEBEB)
                            )
                        }
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
        ) {
            items(messages) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (it.sender == currentPlatform()) {
                        Arrangement.End
                    } else {
                        Arrangement.Start
                    }
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        shape = RoundedCornerShape(6.dp),
                        elevation = 4.dp,
                        backgroundColor = if (it.sender == currentPlatform()) Color(0xFFF2F2F2) else Color.White
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                        ) {
                            Text(
                                text = it.message,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                )
                            )
                            Text(
                                text = it.sender,
                                style = TextStyle(
                                    color = Color(0xFFB4B4B4),
                                    fontSize = 8.sp,
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}