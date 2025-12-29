package com.project.daynergy.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.daynergy.core.viewmodel.TaskViewModel
import com.project.daynergy.core.viewmodel.EnergyViewModel
import com.project.daynergy.core.datastore.TaskEntity
import com.project.daynergy.ui.home.*
import java.time.*
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.max
import kotlin.math.min
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState

enum class TaskStatusFilter {
    ALL, TODO, IN_PROGRESS, COMPLETED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayTasksScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    energyViewModel: EnergyViewModel
) {

    val tasks by taskViewModel.tasks.collectAsState()
    val selectedEnergy by energyViewModel.selectedEnergy.collectAsState()

    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }
    var currentMonth by remember { mutableStateOf(YearMonth.from(today)) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(TaskStatusFilter.ALL) }

    LaunchedEffect(selectedDate) {
        currentMonth = YearMonth.from(selectedDate)
    }

    /* ---------------- FILTERING ---------------- */

    val tasksForDate = tasks.filter { it.date == selectedDate }

    val statusFiltered = when (selectedFilter) {
        TaskStatusFilter.ALL -> tasksForDate
        TaskStatusFilter.TODO -> tasksForDate.filter { !it.isCompleted }
        TaskStatusFilter.IN_PROGRESS -> tasksForDate.filter { !it.isCompleted }
        TaskStatusFilter.COMPLETED -> tasksForDate.filter { it.isCompleted }
    }

    val fitsEnergyTasks = statusFiltered.filter { it.energy == selectedEnergy }
    val saveForLaterTasks = statusFiltered.filter { it.energy != selectedEnergy }

    /* ---------------- UI ---------------- */

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { TodayTasksTopBar() }

        item {
            MonthHeader(
                month = currentMonth,
                onClick = { showDatePicker = true }
            )
        }

        item {
            MonthDaySelector(
                month = currentMonth,
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )
        }

        item {
            StatusFilters(
                selected = selectedFilter,
                onSelected = { selectedFilter = it }
            )
        }

        /* -------- EMPTY STATES -------- */

        if (tasksForDate.isEmpty()) {
            item {
                Spacer(Modifier.height(24.dp))
                Text(
                    "No tasks for today. Add one and start fresh ✨",
                    color = Color.Gray
                )
            }
        } else {

            if (fitsEnergyTasks.isNotEmpty()) {

                item {
                    SectionTitle("Fits Your Energy")
                    Text(
                        text = "Based on your current energy: ${selectedEnergy.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(fitsEnergyTasks) { task ->
                    TaskItem(task, selectedDate, taskViewModel)
                }

                item { Spacer(Modifier.height(12.dp)) }
            } else {
                item {
                    Text(
                        "No tasks match your current energy. You can save others for later 🌱",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }

            if (saveForLaterTasks.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    SectionTitle("Save for Later")
                }

                items(saveForLaterTasks) { task ->
                    TaskItem(task, selectedDate, taskViewModel)
                }
            }
        }

        item { Spacer(Modifier.height(100.dp)) }
    }

    /* ---------------- DATE PICKER ---------------- */

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            }
        ) {
            val state = rememberDatePickerState(
                initialSelectedDateMillis =
                    selectedDate.atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
            )

            DatePicker(state = state)

            state.selectedDateMillis?.let {
                selectedDate = Instant.ofEpochMilli(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }
        }
    }
}

/* ---------------- TASK ITEM ---------------- */

@Composable
private fun TaskItem(
    task: TaskEntity,
    selectedDate: LocalDate,
    taskViewModel: TaskViewModel
) {
    TaskCard(
        title = task.title,
        time = selectedDate.toString(),
        status = task.isCompleted.toTaskStatus(),
        energy = task.energy.toEnergyUi(),
        group = TaskGroup.fromTitle(task.group),

        onStatusChange = { newStatus ->
            taskViewModel.updateTask(
                task.copy(
                    isCompleted = newStatus == TaskStatus.COMPLETED
                )
            )
        },

        onDelete = {
            taskViewModel.deleteTask(task)
        }
    )
}



@Composable
private fun MonthHeader(
    month: YearMonth,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${month.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${month.year}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Open calendar"
        )
    }
}

@Composable
private fun MonthDaySelector(
    month: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = month.lengthOfMonth()
    val listState = rememberLazyListState()

    LaunchedEffect(month, selectedDate) {
        val index = selectedDate.dayOfMonth - 1
        listState.scrollToItem(
            min(max(index - 2, 0), daysInMonth - 1)
        )
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(daysInMonth) { index ->
            val date = month.atDay(index + 1)
            val selected = date == selectedDate

            Column(
                modifier = Modifier
                    .width(64.dp)
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(16.dp)
                    )
                    .clickable { onDateSelected(date) }
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ),
                    fontSize = 10.sp,
                    color =
                        if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    color =
                        if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun StatusFilters(
    selected: TaskStatusFilter,
    onSelected: (TaskStatusFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TaskStatusFilter.entries.forEach { filter ->
            Box(
                modifier = Modifier
                    .background(
                        if (selected == filter)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(50)
                    )
                    .clickable { onSelected(filter) }
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            ) {
                Text(
                    text = filter.name.replace("_", " "),
                    fontSize = 12.sp,
                    color =
                        if (selected == filter)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 1 // extra safety
                )
            }
        }
    }
}




/* ---------------- HELPERS ---------------- */

private fun Boolean.toTaskStatus(): TaskStatus =
    if (this) TaskStatus.COMPLETED else TaskStatus.TODO

@Composable
private fun TodayTasksTopBar() {
    Text(
        text = "Today’s Tasks",
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/* ---------------- ENERGY MAPPER ---------------- */

private fun EnergyLevel.toEnergyUi(): EnergyUi =
    when (this) {
        EnergyLevel.LOW -> EnergyUi.LOW
        EnergyLevel.MEDIUM -> EnergyUi.MEDIUM
        EnergyLevel.HIGH -> EnergyUi.HIGH
    }
