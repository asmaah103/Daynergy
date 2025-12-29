package com.project.daynergy.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.daynergy.core.datastore.TaskEntity
import com.project.daynergy.core.datastore.ThemeMode
import com.project.daynergy.core.navigation.Screen
import com.project.daynergy.core.viewmodel.EnergyViewModel
import com.project.daynergy.core.viewmodel.TaskViewModel
import com.project.daynergy.core.viewmodel.ThemeViewModel
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
    energyViewModel: EnergyViewModel = viewModel(),
    themeViewModel: ThemeViewModel = viewModel()
) {

    val tasks by taskViewModel.tasks.collectAsState()
    val selectedEnergy by energyViewModel.selectedEnergy.collectAsState()
    val themeMode by themeViewModel.themeMode.collectAsState()

    /* ---------------- TODAY PROGRESS ---------------- */

    val completedTasks = tasks.count { it.isCompleted }
    val todayProgress =
        if (tasks.isEmpty()) 0
        else ((completedTasks.toFloat() / tasks.size) * 100).roundToInt()

    /* ---------------- IN PROGRESS ---------------- */

    val inProgressTasks = tasks.filter { !it.isCompleted }

    /* ---------------- TASK GROUPS ---------------- */

    val allGroups = listOf(
        TaskGroup.OFFICE,
        TaskGroup.PERSONAL,
        TaskGroup.STUDY
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        /* ---------------- HEADER ---------------- */

        item {
            HomeHeader(
                themeMode = themeMode,
                onThemeChange = { themeViewModel.setTheme(it) }
            )
        }

        /* ---------------- ENERGY QUESTION ---------------- */

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "How’s your energy right now?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(12.dp))

                EnergySelector(
                    selected = selectedEnergy,
                    onSelected = { energyViewModel.setEnergy(it) }
                )
            }
        }

        /* ---------------- TODAY CARD ---------------- */

        item {
            ProgressCard(
                progress = todayProgress,
                onViewTask = {
                    navController.navigate(Screen.TodayTasks.route)
                }
            )
        }

        /* ---------------- IN PROGRESS ---------------- */

        item {
            SectionHeader(
                title = "In Progress",
                count = inProgressTasks.size
            )
        }

        item {
            if (inProgressTasks.isEmpty()) {
                Text(
                    text = "No tasks in progress ✨",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(inProgressTasks) { task ->
                        InProgressCard(task)
                    }
                }
            }
        }

        /* ---------------- TASK GROUPS ---------------- */

        item {
            SectionHeader(
                title = "Task Groups",
                count = allGroups.size
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                allGroups.forEach { group ->

                    val groupTasks = tasks.filter { it.group == group.title }
                    val completed = groupTasks.count { it.isCompleted }

                    val progress =
                        if (groupTasks.isEmpty()) 0
                        else ((completed.toFloat() / groupTasks.size) * 100).roundToInt()

                    TaskGroupCard(
                        group = group,
                        taskCount = groupTasks.size,
                        progress = progress
                    )
                }
            }
        }

        item { Spacer(Modifier.height(100.dp)) }
    }
}

/* ---------------- HEADER ---------------- */

@Composable
private fun HomeHeader(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = "Hello 👋",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = "Sara",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {
                val next = when (themeMode) {
                    ThemeMode.LIGHT -> ThemeMode.DARK
                    ThemeMode.DARK -> ThemeMode.SYSTEM
                    ThemeMode.SYSTEM -> ThemeMode.LIGHT
                }
                onThemeChange(next)
            }) {
                Icon(
                    imageVector = when (themeMode) {
                        ThemeMode.LIGHT -> Icons.Default.LightMode
                        ThemeMode.DARK -> Icons.Default.DarkMode
                        ThemeMode.SYSTEM -> Icons.Default.Settings
                    },
                    contentDescription = "Theme Mode",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/* ---------------- IN PROGRESS CARD ---------------- */

@Composable
private fun InProgressCard(task: TaskEntity) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {

        Text(
            text = task.group,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = task.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = if (task.isCompleted) 1f else 0.4f,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/* ---------------- SECTION HEADER ---------------- */

@Composable
private fun SectionHeader(
    title: String,
    count: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = count.toString(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
