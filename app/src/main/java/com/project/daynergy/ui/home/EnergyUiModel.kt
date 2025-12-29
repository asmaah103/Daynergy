package com.project.daynergy.ui.home

import androidx.compose.ui.graphics.Color
import com.project.daynergy.R

enum class EnergyUi(
    val label: String,
    val iconRes: Int,
    val bgColor: Color
) {

    LOW(
        label = "Low",
        iconRes = R.drawable.ic_energy_low,
        bgColor = Color(0xFFEDE9FF) // soft lavender
    ),

    MEDIUM(
        label = "Medium",
        iconRes = R.drawable.ic_energy_medium,
        bgColor = Color(0xFFFFF1D6) // soft yellow
    ),

    HIGH(
        label = "High",
        iconRes = R.drawable.ic_energy_high,
        bgColor = Color(0xFFDFF5EA) // soft green
    );

    companion object {
        fun from(value: String?): EnergyUi {
            return values().firstOrNull {
                it.name.equals(value, ignoreCase = true)
            } ?: MEDIUM
        }
    }
}
