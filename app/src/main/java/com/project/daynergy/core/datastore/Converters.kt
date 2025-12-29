package com.project.daynergy.core.datastore
import androidx.room.TypeConverter
import java.time.LocalDate
import com.project.daynergy.ui.home.EnergyLevel


class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String =
        date.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDate =
        LocalDate.parse(value)

    @TypeConverter
    fun fromEnergyLevel(energy: EnergyLevel): String =
        energy.name

    @TypeConverter
    fun toEnergyLevel(value: String): EnergyLevel =
        EnergyLevel.valueOf(value)
}
