package com.example.todolisttask.models.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolisttask.models.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val userDao: UserDao) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val user = userDao.getUserByUsernameAndPassword(username, password)
                    _currentUser.value = user
                }
            } catch (e: Exception) {
                println("Error during login: ${e.message}")
            }
        }
    }
    fun updateCurrentUset(id:Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = userDao.getByUid(id)
                // Обновляем _currentUser после успешной аутентификации
                _currentUser.value = user
            }
        }
    }
}