package com.project.daynergy.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.project.daynergy.ui.home.EnergyLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "energy_prefs")

class EnergyPreferences(private val context: Context) {

    companion object {
        private val ENERGY_KEY = stringPreferencesKey("selected_energy")
    }

    val selectedEnergy: Flow<EnergyLevel> =
        context.dataStore.data.map { prefs ->
            EnergyLevel.valueOf(
                prefs[ENERGY_KEY] ?: EnergyLevel.MEDIUM.name
            )
        }

    suspend fun saveEnergy(energy: EnergyLevel) {
        context.dataStore.edit { prefs ->
            prefs[ENERGY_KEY] = energy.name
        }
    }
}
