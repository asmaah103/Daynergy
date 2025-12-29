package com.project.daynergy.ui.addtask

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.daynergy.R
import com.project.daynergy.core.datastore.TaskEntity
import com.project.daynergy.core.viewmodel.TaskViewModel
import com.project.daynergy.ui.common.IconWithBackground
import com.project.daynergy.ui.home.EnergyLevel
import com.project.daynergy.ui.home.EnergyUi
import com.project.daynergy.ui.home.TaskGroup
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel
) {

    /* ---------------- STATE ---------------- */
    var taskGroup by remember { mutableStateOf(TaskGroup.OFFICE) }
    var energy by remember { mutableStateOf(EnergyUi.HIGH) }

    var taskName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var groupExpanded by remember { mutableStateOf(false) }
    var energyExpanded by remember { mutableStateOf(false) }

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }

    /* ---------------- SCREEN ---------------- */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        /* ---------- TOP BAR ---------- */
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = R.drawable.ic_back2),
                contentDescription = "Back",
                modifier = Modifier.clickable { navController.popBackStack() },
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Add Task",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications",
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Spacer(Modifier.height(24.dp))

        /* ---------- TASK GROUP ---------- */
        Box {
            DropdownFieldCustom(
                label = "Task Group",
                value = taskGroup.title,
                leading = {
                    IconWithBackground(
                        iconRes = taskGroup.iconRes,
                        bgColor = energy.bgColor
                    )
                },
                onClick = { groupExpanded = true }
            )

            DropdownMenu(
                expanded = groupExpanded,
                onDismissRequest = { groupExpanded = false }
            ) {
                TaskGroup.values().forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                it.title,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            taskGroup = it
                            groupExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ---------- ENERGY ---------- */
        Box {
            DropdownFieldCustom(
                label = "Energy Required",
                value = energy.label,
                leading = {
                    IconWithBackground(
                        iconRes = energy.iconRes,
                        bgColor = energy.bgColor
                    )
                },
                onClick = { energyExpanded = true }
            )

            DropdownMenu(
                expanded = energyExpanded,
                onDismissRequest = { energyExpanded = false }
            ) {
                EnergyUi.values().forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                it.label,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            energy = it
                            energyExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ---------- TASK NAME ---------- */
        ThemedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = "Task Name"
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- DESCRIPTION ---------- */
        ThemedTextField(
            value = description,
            onValueChange = { description = it },
            label = "Description",
            height = 120.dp
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- START DATE ---------- */
        DateField(
            label = "Start Date",
            value = startDate.format(formatter),
            onClick = { showStartPicker = true }
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- END DATE ---------- */
        DateField(
            label = "End Date",
            value = endDate.format(formatter),
            onClick = { showEndPicker = true }
        )

        Spacer(Modifier.weight(1f))

        /* ---------- BUTTON ---------- */
        Button(
            onClick = {
                val task = TaskEntity(
                    title = taskName,
                    description = description,
                    date = startDate,
                    group = taskGroup.title,
                    energy = energy.toEnergyLevel()
                )
                taskViewModel.addTask(task)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(26.dp)
        ) {
            Text(
                text = "Add Project",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    /* ---------------- DATE PICKERS ---------------- */
    if (showStartPicker) {
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton = {
                TextButton(onClick = { showStartPicker = false }) {
                    Text("OK")
                }
            }
        ) {
            val state = rememberDatePickerState()
            DatePicker(state)
            state.selectedDateMillis?.let {
                startDate = Instant.ofEpochMilli(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
        }
    }

    if (showEndPicker) {
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton = {
                TextButton(onClick = { showEndPicker = false }) {
                    Text("OK")
                }
            }
        ) {
            val state = rememberDatePickerState()
            DatePicker(state)
            state.selectedDateMillis?.let {
                endDate = Instant.ofEpochMilli(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
        }
    }
}


@Composable
private fun ThemedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    height: Dp? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .let { if (height != null) it.height(height) else it },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun DropdownFieldCustom(
    label: String,
    value: String,
    leading: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Column {
        Text(
            label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(16.dp)
                )
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leading()
            Spacer(Modifier.width(12.dp))
            Text(
                value,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun DateField(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Column {
        Text(
            label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    RoundedCornerShape(16.dp)
                )
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(Modifier.width(12.dp))
            Text(
                value,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/* ---------- mapping ---------- */

private fun EnergyUi.toEnergyLevel(): EnergyLevel {
    return when (this) {
        EnergyUi.LOW -> EnergyLevel.LOW
        EnergyUi.MEDIUM -> EnergyLevel.MEDIUM
        EnergyUi.HIGH -> EnergyLevel.HIGH
    }
}
