package com.project.daynergy.core.repository
import com.project.daynergy.core.datastore.TaskDao
import com.project.daynergy.core.datastore.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskRepository(
    private val taskDao: TaskDao
) {

    fun getAllTasks(): Flow<List<TaskEntity>> =
        taskDao.getAllTasks()

    fun getTasksByDate(date: LocalDate): Flow<List<TaskEntity>> =
        taskDao.getTasksByDate(date)

    suspend fun insertTask(task: TaskEntity) =
        taskDao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) =
        taskDao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

}
