package com.project.daynergy.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object LetsStart : Screen("lets_start")
    object Home : Screen("home")
    object TodayTasks : Screen("today_tasks")
    object AddTask : Screen("add_task")
    object TaskDetails : Screen(
        "task_details/{title}/{description}/{energy}/{status}"
    ) {
        fun createRoute(
            title: String,
            description: String,
            energy: String,
            status: String
        ): String {
            return "task_details/$title/$description/$energy/$status"
        }
    }    object Quotes : Screen("quotes")
}
