package com.project.daynergy.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.daynergy.ui.splash.SplashScreen
import com.project.daynergy.ui.letsstart.LetsStartScreen
import com.project.daynergy.ui.home.HomeScreen
import com.project.daynergy.ui.tasks.TodayTasksScreen
import com.project.daynergy.ui.addtask.AddTaskScreen
import com.project.daynergy.ui.details.TaskDetailsScreen
import com.project.daynergy.ui.quotes.MotivationalQuotesScreen
import com.project.daynergy.ui.home.EnergyUi
import com.project.daynergy.ui.AppBackground
import com.project.daynergy.core.viewmodel.TaskViewModel
import com.project.daynergy.core.viewmodel.EnergyViewModel

@Composable
fun DaynergyNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    taskViewModel: TaskViewModel
) {
    val energyViewModel: EnergyViewModel = viewModel()

    AppBackground {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }

            composable(Screen.LetsStart.route) {
                LetsStartScreen(navController)
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    taskViewModel = taskViewModel,
                    energyViewModel = energyViewModel
                )
            }

            composable(Screen.TodayTasks.route) {
                TodayTasksScreen(
                    navController = navController,
                    taskViewModel = taskViewModel,
                    energyViewModel = energyViewModel
                )
            }

            composable(Screen.AddTask.route) {
                AddTaskScreen(
                    navController = navController,
                    taskViewModel = taskViewModel
                )
            }

            composable(
                route = Screen.TaskDetails.route,
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("description") { type = NavType.StringType },
                    navArgument("energy") { type = NavType.StringType },
                    navArgument("status") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                TaskDetailsScreen(
                    navController = navController,
                    title = backStackEntry.arguments?.getString("title").orEmpty(),
                    description = backStackEntry.arguments?.getString("description").orEmpty(),
                    energy = EnergyUi.from(
                        backStackEntry.arguments?.getString("energy")
                    ),
                    status = backStackEntry.arguments?.getString("status").orEmpty()
                )
            }

            composable(Screen.Quotes.route) {
                MotivationalQuotesScreen(navController)
            }
        }
    }
}
