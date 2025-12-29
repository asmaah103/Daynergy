package com.project.daynergy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.daynergy.core.viewmodel.TaskViewModel
import com.project.daynergy.core.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }

        val app = application as DaynergyApplication
        val taskRepository = app.taskRepository

        setContent {
            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(taskRepository)
            )

            DaynergyApp(
                taskViewModel = taskViewModel
            )
        }
    }
}

