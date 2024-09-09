package com.kashif.composemptweaks.domain

import composemptweaks.shared.generated.resources.Res
import composemptweaks.shared.generated.resources.clap
import composemptweaks.shared.generated.resources.curious
import composemptweaks.shared.generated.resources.heart
import composemptweaks.shared.generated.resources.ic_check_big
import composemptweaks.shared.generated.resources.ic_eye
import composemptweaks.shared.generated.resources.ic_send
import composemptweaks.shared.generated.resources.insightful
import composemptweaks.shared.generated.resources.like
import composemptweaks.shared.generated.resources.support
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(InternalResourceApi::class)
object DrawableHelper {
    val CLAP = Res.drawable.clap
    val CURIOUS = Res.drawable.curious
    val HEART =Res.drawable.heart
    val INSIGHTFUL =Res.drawable.insightful
    val LIKE =Res.drawable.like
    val SUPPORT = Res.drawable.support

    val IC_SEND = Res.drawable.ic_send
    val IC_EYE = Res.drawable.ic_eye
    val IC_CHECK_BIG = Res.drawable.ic_check_big
}