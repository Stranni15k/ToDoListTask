package com.example.todolisttask.models.composeui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todolisttask.AppDatabase
import com.example.todolisttask.composeui.navigation.Screen
import com.example.todolisttask.models.model.AuthViewModel
import com.example.todolisttask.models.model.Task
import com.example.todolisttask.models.model.ViewModels.TaskViewModel
import com.example.todolisttask.models.model.ViewModels.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun TaskList(navController: NavController, authViewModel: AuthViewModel, taskViewModel: TaskViewModel, userViewModel: UserViewModel, userId: Int, showFavorites: Boolean = false) {

    val currentUser by userViewModel.user.collectAsState()
    taskViewModel.getTasksByUser(userId)
    var tasks = taskViewModel.tasks.value

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userId != authViewModel.currentUser?.value?.uid ?: -1) {
            item {
                Text(
                    text = currentUser?.name + " (" + currentUser?.login + ")",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        item {
            FlowColumn() {
                tasks.forEach { task ->
                    val taskName = task.name
                    val taskDescription = task.description

                    FlowRow(
                        modifier = Modifier
                            .padding(8.dp)
                            .border(
                                1.dp,
                                Color.Black,
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                navController?.navigate(
                                    Screen.EditTask.route.replace(
                                        "{id}",
                                        task.uid.toString()
                                    )
                                )
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Изменить"
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 4.dp)
                        ) {
                            Text(
                                text = taskName,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            if (taskDescription.isNotBlank()) {
                                Text(
                                    text = taskDescription
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                taskViewModel.viewModelScope.launch {
                                    taskViewModel.deleteTask(task)
                                    /* userViewModel.deleteTask(
                                         currentUser?.id ?: 0,
                                         task.id
                                     )*/
                                    authViewModel.updateCurrentUset(authViewModel.currentUser?.value?.uid ?:-1)
                                    taskViewModel.updateTasks(taskViewModel.tasks.value)
                                }

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Удалить"
                            )
                        }
                      /*  IconButton(
                            onClick = {
                                userViewModel.addFavoriteTaskToUser(userId, task)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Добавить в избранное"
                            )
                        }*/
                    }
                }
            }
        }
    }
}