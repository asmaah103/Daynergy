package com.project.daynergy.ui.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    COMPLETED;

    fun next(): TaskStatus {
        return when (this) {
            TODO -> IN_PROGRESS
            IN_PROGRESS -> COMPLETED
            COMPLETED -> TODO
        }
    }

    @Composable
    fun label(): String =
        when (this) {
            TODO -> "To-do"
            IN_PROGRESS -> "In Progress"
            COMPLETED -> "Completed"
        }

    @Composable
    fun bgColor(): Color =
        when (this) {
            TODO -> MaterialTheme.colorScheme.surfaceVariant
            IN_PROGRESS -> MaterialTheme.colorScheme.secondaryContainer
            COMPLETED -> MaterialTheme.colorScheme.primaryContainer
        }

    @Composable
    fun textColor(): Color =
        when (this) {
            TODO -> MaterialTheme.colorScheme.onSurfaceVariant
            IN_PROGRESS -> MaterialTheme.colorScheme.onSecondaryContainer
            COMPLETED -> MaterialTheme.colorScheme.onPrimaryContainer
        }
}
