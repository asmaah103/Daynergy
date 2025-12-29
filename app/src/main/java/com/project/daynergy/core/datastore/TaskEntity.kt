package com.project.daynergy.core.datastore
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import com.project.daynergy.ui.home.EnergyLevel


@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String,
    val date: LocalDate,
    val group: String,
    val energy: EnergyLevel,
    val isCompleted: Boolean = false
)
