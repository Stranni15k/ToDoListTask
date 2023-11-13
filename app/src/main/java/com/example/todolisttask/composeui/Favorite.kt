package com.example.todolisttask.composeui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolisttask.models.model.AuthViewModel
import com.example.todolisttask.models.model.TaskViewModel
import com.example.todolisttask.models.model.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun Favorite(navController: NavController, authViewModel: AuthViewModel, taskViewModel: TaskViewModel, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TaskList(navController, authViewModel, taskViewModel, userViewModel, authViewModel.currentUser?.id ?: -1, showFavorites = true)
    }
}