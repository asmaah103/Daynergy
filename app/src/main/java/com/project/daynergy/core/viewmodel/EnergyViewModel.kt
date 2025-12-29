package com.project.daynergy.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.daynergy.core.datastore.EnergyPreferences
import com.project.daynergy.ui.home.EnergyLevel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EnergyViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = EnergyPreferences(application)

    val selectedEnergy: StateFlow<EnergyLevel> =
        preferences.selectedEnergy
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = EnergyLevel.MEDIUM
            )

    fun setEnergy(energy: EnergyLevel) {
        viewModelScope.launch {
            preferences.saveEnergy(energy)
        }
    }
}
