package com.project.daynergy

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.daynergy.core.navigation.DaynergyNavGraph
import com.project.daynergy.core.navigation.Screen
import com.project.daynergy.core.viewmodel.ThemeViewModel
import com.project.daynergy.ui.BottomNav
import com.project.daynergy.ui.theme.DaynergyTheme
import com.project.daynergy.core.viewmodel.TaskViewModel

@Composable
fun DaynergyApp(
    taskViewModel: TaskViewModel

) {

    val themeViewModel: ThemeViewModel = viewModel()
    val themeMode by themeViewModel.themeMode.collectAsState()

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomNav = currentRoute in listOf(
        Screen.Home.route,
        Screen.TodayTasks.route,
        Screen.Quotes.route
    )

    DaynergyTheme(themeMode = themeMode) {
        Scaffold(
            bottomBar = {
                if (showBottomNav) {
                    BottomNav(navController)
                }
            }
        ) { paddingValues ->
            DaynergyNavGraph(
                navController = navController,
                paddingValues = paddingValues,
                taskViewModel = taskViewModel
            )
        }
    }
}
