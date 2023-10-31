package com.example.todolisttask.models.model

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val login: String,
    val password: String,
    val taskId: List<Task>,
    val favoritetask: List<Task>
) : Serializable


class UserViewModel : ViewModel() {
    var users: MutableState<List<User>> = mutableStateOf(
        listOf(
            User(0, "Иван", "ivan", "111111", emptyList(), emptyList()),
            User(1, "Анна", "ann", "111111", emptyList(), emptyList()),
            User(2, "Лиза", "liza", "111111", emptyList(), emptyList())
        )
    )

    fun getUsers(): List<User> {
        return users.value
    }
    fun getUser(id:Int): User {
        return users.value.get(id)
    }
    fun getPets(id:Int): List<Task> {
        return users.value.get(id).taskId
    }
    // Function to add a pet to a user
    fun addPetToUser(userId: Int, newPet: Task) {
        val updatedUsers = users.value.toMutableList()
        val user = updatedUsers.find { it.id == userId }

        user?.let {
            val updatedPets = user.taskId.toMutableList()
            updatedPets.add(newPet)
            val updatedUser = user.copy(taskId = updatedPets)
            val userIndex = updatedUsers.indexOf(user)
            updatedUsers[userIndex] = updatedUser
            users.value = updatedUsers
        }
    }
    fun deleteTask(userId: Int, taskIdToDelete: Int) {
        val updatedUsers = users.value.toMutableList()

        // Найдем пользователя по userId
        val user = updatedUsers.find { it.id == userId }

        user?.let { user ->
            val updatedTasks = user.taskId.toMutableList()

            // Найдем питомца по petId и удалим его из списка питомцев
            val taskToDelete = updatedTasks.find { it.id == taskIdToDelete }

            taskToDelete?.let { task ->
                updatedTasks.remove(task)
            }

            // Обновим пользователя с обновленным списком питомцев
            val updatedUser = user.copy(taskId = updatedTasks)

            // Обновим список пользователей
            val userIndex = updatedUsers.indexOf(user)
            updatedUsers[userIndex] = updatedUser
            users.value = updatedUsers
        }
    }

    fun addFavoriteTaskToUser(userId: Int, newFavoriteTask: Task) {
        val updatedUsers = users.value.toMutableList()
        val user = updatedUsers.find { it.id == userId }

        user?.let {
            val updatedFavoriteTasks = user.favoritetask.toMutableList()
            updatedFavoriteTasks.add(newFavoriteTask)
            val updatedUser = user.copy(favoritetask = updatedFavoriteTasks)
            val userIndex = updatedUsers.indexOf(user)
            updatedUsers[userIndex] = updatedUser
            users.value = updatedUsers
        }
    }


    fun updateTaskOnUser(userId: Int, updatedTask: Task) {
        val updatedUsers = users.value.toMutableList()

        // Найдем пользователя по userId
        val user = updatedUsers.find { it.id == userId }

        user?.let { user ->
            val updatedTasks = user.taskId.toMutableList()

            // Найдем питомца по petId и обновим его
            val taskToUpdate = updatedTasks.find { it.id == updatedTask.id }

            taskToUpdate?.let { task ->
                val taskIndex = updatedTasks.indexOf(task)
                updatedTasks[taskIndex] = updatedTask
            }

            // Обновим пользователя с обновленным списком питомцев
            val updatedUser = user.copy(taskId = updatedTasks)

            // Обновим список пользователей
            val userIndex = updatedUsers.indexOf(user)
            updatedUsers[userIndex] = updatedUser
            users.value = updatedUsers
        }
    }

    fun updateUser(updatedUser: User) {
        val updatedUsers = users.value.toMutableList()
        val index = updatedUsers.indexOfFirst { it.login == updatedUser.login }
        if (index != -1) {
            updatedUsers[index] = updatedUser
            users.value = updatedUsers
        }
    }
}
