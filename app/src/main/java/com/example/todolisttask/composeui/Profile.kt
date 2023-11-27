package com.example.todolisttask.composeui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolisttask.composeui.navigation.Screen
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun Profile(navController: NavController,currentUser: User?, onSaveClick: (User) -> Unit) {
    val nameState = remember { mutableStateOf(currentUser?.name.orEmpty()) }
    val loginState = remember { mutableStateOf(currentUser?.login.orEmpty()) }
    val passwordState = remember { mutableStateOf(currentUser?.password.orEmpty()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = loginState.value,
            onValueChange = { loginState.value = it },
            label = { Text("Login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val updatedUser = User(
                    currentUser?.uid ?: 0,
                    nameState.value,
                    loginState.value,
                    passwordState.value,
                )
                onSaveClick(updatedUser)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }

}
