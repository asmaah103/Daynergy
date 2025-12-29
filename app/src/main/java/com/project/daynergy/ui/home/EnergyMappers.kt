package com.project.daynergy.ui.home

fun EnergyLevel.toEnergyUi(): EnergyUi =
    when (this) {
        EnergyLevel.LOW -> EnergyUi.LOW
        EnergyLevel.MEDIUM -> EnergyUi.MEDIUM
        EnergyLevel.HIGH -> EnergyUi.HIGH
    }
