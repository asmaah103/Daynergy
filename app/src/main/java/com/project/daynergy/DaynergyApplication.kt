package com.project.daynergy

import android.app.Application
import androidx.room.Room
import com.project.daynergy.core.datastore.AppDatabase
import com.project.daynergy.core.repository.TaskRepository

class DaynergyApplication : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var taskRepository: TaskRepository
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "daynergy_db"
        ).build()

        taskRepository = TaskRepository(
            database.taskDao()
        )
    }
}
