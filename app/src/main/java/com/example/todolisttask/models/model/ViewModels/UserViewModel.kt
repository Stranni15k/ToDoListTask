package com.example.todolisttask.models.model.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolisttask.models.dao.UserDao
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    // Метод для обновления списка пользователей
    fun updateUsers(newUsers: List<User>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _users.value = newUsers
            }
        }
    }

    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.e("TAG", "111111111111111111111")
                // Update the specific pet in the database
                userDao.update(updatedUser)

                // Retrieve the updated list of pets as a Flow
                val updatedUsersFlow = userDao.getAll()
                Log.e("TAG", "222222222222222222222222")
                // Collect the values from the Flow and update _pets
                updatedUsersFlow.collect { updatedUsers ->
                    // Convert java.util.List to kotlin.collections.List if needed
                    _users.value = updatedUsers.toList()
                }
                Log.e("TAG", "333333333333333333333333")

            }
        }
    }

    fun getUser(userId: Int): StateFlow<User?> {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = userDao.getByUid(userId)
                _user.value = user
            }
        }
        return _user
    }

    // Метод для входа пользователя
    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = userDao.getUserByUsernameAndPassword(login, password)
                // Здесь должно быть обновление списка пользователей, но не _currentUser
                // Также, вам, возможно, нужно вызвать метод updateUsers с обновленным списком пользователей
            }
        }
    }
}