package com.kashif.composemptweaks

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPView(
    numberOfFields: Int = 4,
    onValueChange: (String) -> Unit
) {
    var otp by remember {
        mutableStateOf("")
    }

    val focuses = remember {
        List(numberOfFields) {
            FocusRequester()
        }
    }

    val focusManager = LocalFocusManager.current


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(numberOfFields) { index ->
            OutlinedTextField(
                value = otp.tryGet(index),
                onValueChange = {
                    if (otp.length <= numberOfFields) {
                        if (it.length == 1) {
                            //  it means user is typing the otp, so move the focus to next tf as the otp is entered
                            focuses[index].freeFocus()
                            if (index < numberOfFields - 1)
                                focuses[index + 1].requestFocus()
                            else
                                focusManager.clearFocus(force = true)

                            otp += it
                        } else if (it.length == numberOfFields) {
                            //means user is pasting the otp, so use the text and clear focus.
                            otp = it
                            focusManager.clearFocus(true)
                        }
                        onValueChange(otp)
                    }
                },
                modifier = Modifier
                    .focusRequester(focuses[index])
                    .onKeyEvent { event ->
                        if (event.key == Key.Backspace) {
                            if (index > 0) {
                                if (index == otp.length - 1)  //move focus to prev tf and drop the last digit from otp
                                    focuses[index - 1].requestFocus()
                                else { //move the focus to the field from which the letter is removed
                                    focuses[otp.length - 1].requestFocus()
                                }
                            } else
                                focusManager.clearFocus(true)


                            otp = otp.dropLast(1)
                            onValueChange(otp)
                        }
                        true
                    }
                    .height(50.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.None
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    if (index > 0 && otp.isEmpty()) //force the focus to go to first tf even if user click on any other field if the text is empty yet
                                        focuses[0].requestFocus()
                                    else if (otp.length < numberOfFields - 1) //focus the tf next to the previously filled field even if user clicks any other field.
                                        focuses[otp.length].requestFocus()
                                }
                            }
                        }
                    },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color.Transparent,
                    backgroundColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
            )
        }
    }
}


fun String.tryGet(index: Int): String {
    return try {
        this[index].toString()
    } catch (e: Exception) {
        ""
    }
}