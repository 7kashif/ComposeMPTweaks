package com.kashif.composemptweaks.domain

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
object DrawableHelper {
    val CLAP = DrawableResource("drawables/clap.xml")
    val CURIOUS = DrawableResource("drawables/curious.xml")
    val HEART =DrawableResource( "drawables/heart.xml")
    val INSIGHTFUL =DrawableResource( "drawables/insightful.xml")
    val LIKE =DrawableResource( "drawables/like.xml")
    val SUPPORT = DrawableResource("drawables/support.xml")
    val IC_SEND = DrawableResource("drawables/ic_send.xml")
    val IC_EYE = DrawableResource("drawables/ic_eye.xml")
    val IC_CHECK_BIG = DrawableResource("drawables/ic_check_big.xml")
}