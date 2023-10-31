package com.example.todolisttask.models.composeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolisttask.composeui.navigation.Screen
import com.example.todolisttask.models.model.UserViewModel

/*
@Composable

fun UserList(navController: NavController?,
             userViewModel: UserViewModel
) {
    val users = userViewModel.users.value

    Column(Modifier.padding(all = 10.dp)) {
        users.forEachIndexed() { index, user ->
            val userId = Screen.UserViewModel.route.replace("{id}", index.toString())
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                onClick = { navController?.navigate(userId) }) {
                Text("${user.login}")
            }
        }
    }
}*/

