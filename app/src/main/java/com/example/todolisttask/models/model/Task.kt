package com.example.todolisttask.models.model

import android.media.Image
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.io.Serializable

data class Task(
    var id: Int,
    val name: String,
    val description: String
) : Serializable

class TaskViewModel : ViewModel() {
    var tasks: MutableState<List<Task>> = mutableStateOf(
        emptyList()
    )

    fun createTask(newTask: Task): Task {
        newTask.id = tasks.value.size.toInt()
        val updatedTasks = tasks.value.toMutableList()
        updatedTasks.add(newTask)
        tasks.value = updatedTasks
        return newTask
    }

    fun deleteTask(task: Task) {
        val updatedTasks = tasks.value.toMutableList()
        updatedTasks.remove(task)
        tasks.value = updatedTasks
    }

    fun updateTask(updatedTask: Task) {
        val updatedTasks = tasks.value.toMutableList()
        val index = updatedTasks.indexOfFirst { it.id == updatedTask.id }

        if (index != -1) {
            updatedTasks[index] = updatedTask
            tasks.value = updatedTasks
        }
    }
}