package com.example.todolisttask.models.model.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolisttask.models.dao.TaskDao
import com.example.todolisttask.models.model.Task
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.RowId

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> get() = _task
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    // Метод для обновления списка пользователей
    fun updateTasks(newUsers: List<Task>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tasks.value = newUsers
            }
        }
    }
    fun updateTask(updatedTask: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.e("TAG", "111111111111111111111")
                // Update the specific pet in the database
                taskDao.update(updatedTask)

                // Retrieve the updated list of pets as a Flow
                val updatedTasksFlow = taskDao.getAll()
                Log.e("TAG", "222222222222222222222222")
                // Collect the values from the Flow and update _pets
                updatedTasksFlow.collect { updatedTasks ->
                    // Convert java.util.List to kotlin.collections.List if needed
                    _tasks.value = updatedTasks.toList()
                }
                Log.e("TAG", "333333333333333333333333")

            }
        }
    }
    // Метод для создания новой задачи
    fun createTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Вставляем новую задачу в базу данных
                taskDao.insert(task)

                // Получаем актуальный список задач после вставки
                val updatedTasksFlow = taskDao.getAll()
                Log.e("TAG", "222222222222222222222222")
                // Collect the values from the Flow and update _pets
                updatedTasksFlow.collect { updatedTasks ->
                    // Convert java.util.List to kotlin.collections.List if needed
                    _tasks.value = updatedTasks.toList()
                }
            }
        }
    }

    fun getTasksByUser(userId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tasks.value = taskDao.getByUid(userId)
            }
        }
    }
    fun getTask(Id: Int): StateFlow<Task?>  {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val task = taskDao.getOne(Id)
                _task.value = task
            }
        }
        return _task
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                taskDao.delete(task)
            }
        }
    }
}