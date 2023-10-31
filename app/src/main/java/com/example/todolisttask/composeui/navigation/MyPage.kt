package com.example.todolisttask.composeui.navigation


import CreateTask
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination

import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pmuapp.composeui.Profile
import com.example.todolisttask.R
import com.example.todolisttask.composeui.EditTask
import com.example.todolisttask.composeui.Favorite
import com.example.todolisttask.models.composeui.TaskList
import com.example.todolisttask.models.model.AuthViewModel
import com.example.todolisttask.models.model.TaskViewModel
import com.example.todolisttask.models.model.UserViewModel
import com.example.todolisttask.composeui.Login
import com.example.todolisttask.composeui.Home


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(
    navController: NavHostController,
    currentScreen: Screen?
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(stringResource(currentScreen?.resourceId ?: R.string.app_name))
        },
        navigationIcon = {
            if (
                navController.previousBackStackEntry != null
                && (currentScreen == null || !currentScreen.showInBottomBar)
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}

@Composable
fun Navbar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier) {
        Screen.bottomBarItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    taskViewModel: TaskViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Login.route) {
            Login(navController, authViewModel,userViewModel.getUsers())        }
        composable(Screen.Logout.route) {
            Login(navController, authViewModel,userViewModel.getUsers())       }
        composable(Screen.CreateTask.route) {
            val currentUser = authViewModel.currentUser ?: userViewModel.getUsers().firstOrNull()
            CreateTask ( navController, onSaveClick = { newTask ->
                var adedTask = taskViewModel.createTask(newTask)
                userViewModel.addPetToUser(currentUser?.id ?:0, adedTask)
                authViewModel.currentUser = userViewModel.getUser(currentUser?.id ?:0)
            }
                )

            }

        composable(Screen.Favorite.route){
            val currentUser = authViewModel.currentUser ?: userViewModel.getUsers().firstOrNull()
            if (currentUser != null) {
                Favorite(navController,
                    authViewModel,
                    taskViewModel,
                    userViewModel
                )
            } else {
            }

        }

        composable(Screen.Profile.route) {
            val currentUser = authViewModel.currentUser ?: userViewModel.getUsers().firstOrNull()
            if (currentUser != null) {
                Profile(navController,
                    currentUser = currentUser,
                    onSaveClick = {updatedUser ->
                        userViewModel.updateUser(updatedUser)
                        authViewModel.currentUser = updatedUser
                        navController.navigate(Screen.Profile.route)
                    }
                )
            } else {
            }
        }

        composable(Screen.Home.route){
            val currentUser = authViewModel.currentUser ?: userViewModel.getUsers().firstOrNull()
            if (currentUser != null) {
                Home(navController,
                    authViewModel,
                    taskViewModel,
                    userViewModel
                )
            } else {
            }

        }
        composable(
            Screen.EditTask.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getInt("id") ?: -1
                EditTask(navController,authViewModel, taskViewModel, userViewModel, petId)

        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavbar() {
    val navController = rememberNavController()
    val authViewModel = remember { AuthViewModel() }
    val userViewModel = remember {UserViewModel() }
    val taskViewModel = remember {TaskViewModel()}
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = currentDestination?.route?.let { Screen.getItem(it) }

    Scaffold(
        topBar = {
            Topbar(navController, currentScreen)
        },
        bottomBar = {
            if ((currentScreen == null || currentScreen.showInBottomBar) && currentScreen != Screen.Login && currentScreen != Screen.Logout) {
                Navbar(navController, currentDestination)
            }
        }
    ) { innerPadding ->
        NavHost(navController, innerPadding, authViewModel, userViewModel, taskViewModel )
        }
}

//@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun MainNavbarPreview() {
//    PMUappTheme {
//        Surface(
//            color = MaterialTheme.colorScheme.background
//        ) {
//            MainNavbar()
//        }
//    }
//}