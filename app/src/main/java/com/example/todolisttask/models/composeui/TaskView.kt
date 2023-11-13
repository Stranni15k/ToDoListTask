package com.example.todolisttask.models.composeui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolisttask.AppDatabase
import com.example.todolisttask.R
import com.example.todolisttask.models.model.TaskWithUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskView(id: Int) {
    val context = LocalContext.current
    val (taskWithUser, setTaskWithUser) = remember { mutableStateOf<TaskWithUser?>(null) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            setTaskWithUser(AppDatabase.getInstance(context).userDao().getByUid(id))
        }
    }

    taskWithUser?.let { task ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = task.user?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(stringResource(id = R.string.user_name))
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = task.user?.login ?: "",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(stringResource(id = R.string.user_login))
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = task.taskName ?: "",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(stringResource(id = R.string.task_name))
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = task.user?.password ?: "",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(stringResource(id = R.string.password_name))
                }
            )
        }
    }
}