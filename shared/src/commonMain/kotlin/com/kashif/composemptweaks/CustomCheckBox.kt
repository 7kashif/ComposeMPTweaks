package com.kashif.composemptweaks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun CheckBoxWithText(
    modifier: Modifier = Modifier,
    text: String,
    size: Dp = 24.dp,
    checkedBgColor: Color = Color.DarkGray,
    unCheckedBgColor: Color = Color.White,
    borderColor: Color = Color.DarkGray,
    icon: String,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onCheckedChange(checked.not())
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
    ) {
        CustomCheckBox(
            onCheckedChange = onCheckedChange,
            checked = checked,
            size = size,
            checkedBgColor = checkedBgColor,
            unCheckedBgColor = unCheckedBgColor,
            borderColor = borderColor,
            icon = icon,
        )
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    checkedBgColor: Color = Color.DarkGray,
    unCheckedBgColor: Color = Color.White,
    borderColor: Color = Color.DarkGray,
    icon: String,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                onCheckedChange(!checked)
            }
            .run {
                if (checked) {
                    background(color = checkedBgColor)
                } else {
                    background(color = unCheckedBgColor)
                    border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(6.dp))
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column {
            AnimatedVisibility(visible = checked) {
                Image(
                    painter = painterResource(res = icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                )
            }
        }
    }
}