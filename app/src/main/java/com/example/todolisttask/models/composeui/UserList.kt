package com.example.todolisttask.models.composeui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolisttask.AppDatabase
import com.example.todolisttask.composeui.navigation.Screen
import com.example.todolisttask.models.model.Task
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UserList(navController: NavController?) {
    val context = LocalContext.current
    val users = remember { mutableStateListOf<User>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val userList = AppDatabase.getInstance(context).userDao().getAll()
            users.clear()
            users.add(userList)
        }
    }

    Column(Modifier.padding(all = 10.dp)) {
        users.forEach { task ->
            key(task.uid) {
                val taskId = Screen.Home.route.replace("{id}", task.uid.toString())
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    onClick = { navController?.navigate(taskId) }) {
                    Text("${task.name} ${task.login}")
                }
            }
        }
    }
}