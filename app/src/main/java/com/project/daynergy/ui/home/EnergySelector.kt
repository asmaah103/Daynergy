package com.project.daynergy.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EnergySelector(
    selected: EnergyLevel,
    onSelected: (EnergyLevel) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {

        val itemWidth = (maxWidth - 16.dp) / 3   // 2 gaps × 8dp

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            EnergyItem(
                text = "Low",
                selected = selected == EnergyLevel.LOW,
                selectedColor = Color(0xFFFFC1C1),
                width = itemWidth,
                onClick = { onSelected(EnergyLevel.LOW) }
            )

            EnergyItem(
                text = "Medium",
                selected = selected == EnergyLevel.MEDIUM,
                selectedColor = Color(0xFFFFE6A7),
                width = itemWidth,
                onClick = { onSelected(EnergyLevel.MEDIUM) }
            )

            EnergyItem(
                text = "High",
                selected = selected == EnergyLevel.HIGH,
                selectedColor = Color(0xFFC1FFD7),
                width = itemWidth,
                onClick = { onSelected(EnergyLevel.HIGH) }
            )
        }
    }
}

@Composable
private fun EnergyItem(
    text: String,
    selected: Boolean,
    selectedColor: Color,
    width: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(44.dp)
            .background(
                color = if (selected)
                    selectedColor
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected)
                Color.Black
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
        )
    }
}
