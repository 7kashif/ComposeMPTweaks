package com.kashif.composemptweaks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif.composemptweaks.data.dto.MessageDTO
import com.kashif.composemptweaks.domain.ChatViewModel
import com.kashif.composemptweaks.domain.DrawableHelper
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@OptIn(ExperimentalResourceApi::class)
@Composable
fun Chat(
    viewModel: ChatViewModel = koinInject()
) {
    val messages by viewModel.messages.collectAsState()
    val (text, setText) = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.initiateChat()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = setText,
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = BostonBlue,
                        focusedIndicatorColor = BostonBlue,
                        backgroundColor = Color.White.copy(0.6f)
                    ),
                    modifier = Modifier,
                    placeholder = {
                        Text(
                            text = "Write a message...",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                )
                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        viewModel.sendMessage(
                            MessageDTO(
                                sender = "Android",
                                message = text
                            )
                        )
                        setText("")
                    }
                ) {
                    Image(
                        painter = painterResource(res = DrawableHelper.IC_SEND),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 72.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(messages) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(BostonBlue.copy(0.7f))
                        .padding(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = it.sender,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Thin
                        )
                    )
                    Text(
                        text = it.message,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }

}