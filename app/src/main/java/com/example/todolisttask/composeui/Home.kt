package com.example.todolisttask.composeui

import com.example.todolisttask.models.model.ViewModels.TaskViewModel
import com.example.todolisttask.models.model.ViewModels.UserViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolisttask.composeui.navigation.Screen
import com.example.todolisttask.models.composeui.TaskList
import com.example.todolisttask.models.model.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun Home(navController: NavController, authViewModel: AuthViewModel, taskViewModel: TaskViewModel, userViewModel: UserViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.CreateTask.route)
            }
        ) {
            Text("Создать задачу")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TaskList(navController, authViewModel, taskViewModel, userViewModel, authViewModel.currentUser?.value?.uid ?: -1)

    }
}
