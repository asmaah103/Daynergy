package com.project.daynergy.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconWithBackground(
    iconRes: Int,
    bgColor: Color,
    size: Int = 32,
    iconSize: Int = 16,
    radius: Int = 8
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(bgColor, RoundedCornerShape(radius.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(iconSize.dp)
        )
    }
}
