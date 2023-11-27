package com.example.todolisttask.composeui

import android.annotation.SuppressLint
import android.os.Build.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolisttask.composeui.navigation.Screen
import com.example.todolisttask.models.model.AuthViewModel
import com.example.todolisttask.models.model.Task
import com.example.todolisttask.models.model.User
import com.example.todolisttask.models.model.ViewModels.TaskViewModel
import com.example.todolisttask.models.model.ViewModels.UserViewModel
import kotlinx.coroutines.coroutineScope

@RequiresApi(VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun EditTask(navController: NavController, authViewModel: AuthViewModel, taskViewModel: TaskViewModel, userViewModel: UserViewModel, task : Task?, onSaveClick: (Task) -> Unit) {
    var taskName = remember { mutableStateOf(task?.name.orEmpty()) }
    var taskDescriptions = remember { mutableStateOf(task?.description.orEmpty()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = taskName.value,
            onValueChange = { taskName.value = it },
            label = { Text("Название задания") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = taskDescriptions.value,
            onValueChange = { taskDescriptions.value = it },
            label = { Text("Описание задания") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .heightIn(max = 200.dp)
                .padding(16.dp),
            readOnly = false
        )

        Spacer(modifier = Modifier.weight(1f))


     //   val editedTaskId = Screen.PlayTask.route.replace("{id}", task?.id.toString())

        Button(
            onClick = {
                val updatedTask = Task(
                    task?.uid ?:0,
                    taskName.value,
                    taskDescriptions.value,
                    task?.userId ?:0)
                onSaveClick(updatedTask)
                navController.popBackStack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Сохранить")
        }

    }


}