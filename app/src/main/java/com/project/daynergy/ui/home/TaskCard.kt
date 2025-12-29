package com.project.daynergy.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.daynergy.ui.common.IconWithBackground
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

@Composable
fun TaskCard(
    title: String,
    time: String,
    status: TaskStatus,

    energy: EnergyUi,
    group: TaskGroup,

    onStatusChange: (TaskStatus) -> Unit,
    onDelete: () -> Unit,

    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    var showDeleteDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    /* -------- STATUS ANIMATIONS -------- */

    val animatedBgColor by animateColorAsState(
        targetValue = status.bgColor(),
        animationSpec = tween(durationMillis = 250),
        label = "StatusBgColor"
    )

    val animatedTextColor by animateColorAsState(
        targetValue = status.textColor(),
        animationSpec = tween(durationMillis = 250),
        label = "StatusTextColor"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(18.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        /* -------- TEXT -------- */
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = time,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        /* -------- ENERGY ICON -------- */
        IconWithBackground(
            iconRes = energy.iconRes,
            bgColor = energy.bgColor
        )

        Spacer(Modifier.width(8.dp))

        /* -------- GROUP ICON -------- */
        IconWithBackground(
            iconRes = group.iconRes,
            bgColor = group.iconBgColor
        )

        Spacer(Modifier.width(8.dp))

        /* -------- STATUS DROPDOWN -------- */
        Box {
            Row(
                modifier = Modifier
                    .background(
                        animatedBgColor,
                        RoundedCornerShape(50)
                    )
                    .clickable { expanded = true }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = status.label(),
                    fontSize = 11.sp,
                    color = animatedTextColor
                )
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Change status",
                    tint = animatedTextColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                TaskStatus.values().forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label()) },
                        onClick = {
                            expanded = false
                            if (option != status) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onStatusChange(option)
                            }
                        }

                    )
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        /* -------- DELETE -------- */
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete task",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .size(18.dp)
                .clickable { showDeleteDialog = true }
        )
    }

    /* -------- CONFIRM DELETE -------- */
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete task?") },
            text = { Text("This action cannot be undone.") }
        )
    }
}
