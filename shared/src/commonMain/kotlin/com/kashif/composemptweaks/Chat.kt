package com.kashif.composemptweaks

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kashif.composemptweaks.domain.ChatViewModel
import org.koin.compose.koinInject


@Composable
fun Chat(
    viewModel: ChatViewModel = koinInject()
) {

    LaunchedEffect(Unit) {
        viewModel.initiateChat()
    }

    Column {
        Text("Test tf.")
    }

}