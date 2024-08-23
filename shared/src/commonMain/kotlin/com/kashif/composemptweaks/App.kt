package com.kashif.composemptweaks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.core.module.Module

@Composable
fun App() {
    MaterialTheme {
        val (value, setValue) = remember {
            mutableStateOf(0f)
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = value,
                onValueChange = setValue,
                valueRange = 0f..100f,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            PercentageGauge(
                percentage = value
            )
        }


//        val (editable, setEditable) = remember {
//            mutableStateOf(true)
//        }
//        val (draggable, setDraggable) = remember {
//            mutableStateOf(true)
//        }
//        val (roundOff, setRoundOff) = remember {
//            mutableStateOf(true)
//        }
//        val (rating, setRating) = remember {
//            mutableStateOf(1f)
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize().padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Editable")
//                Switch(
//                    checked = editable,
//                    onCheckedChange = {
//                        setEditable(it)
//                        if (!it) {
//                            setDraggable(false)
//                        }
//                    }
//                )
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Draggable")
//                Switch(
//                    checked = draggable && editable,
//                    onCheckedChange = setDraggable
//                )
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Round Off")
//                Switch(
//                    checked = roundOff,
//                    onCheckedChange = setRoundOff
//                )
//            }
//            RatingBarEditable(
//                isEditable = editable,
//                isDraggable = draggable,
//                roundOffToWholeNumber = roundOff,
//                onValueChange = setRating,
//                rating = rating
//            )
//            DotLoader()
//            OTPView()
//        }
    }
}

expect fun platformModule(): Module

expect fun currentPlatform(): String