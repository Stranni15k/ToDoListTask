package com.example.todolisttask.composeui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.todolisttask.R


enum class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector = Icons.Filled.Favorite,
    val showInBottomBar: Boolean = true
) {
    Home(
        "home", R.string.home_title, Icons.Filled.Home
    ),
    Login(
        "login", R.string.login_title, showInBottomBar = false
    ),
    Profile(
        "profile", R.string.user_my_title, Icons.Filled.Person
    ),
    Favorite(
        "favorite", R.string.favorite_task_title, Icons.Filled.Favorite
    ),
    Logout(
        "logout", R.string.logout_title, Icons.Filled.ExitToApp
    ),
    CreateTask(
        "createtask", R.string.create_task_title, showInBottomBar = false
    ),
    EditTask(
        "edittask/{id}", R.string.edit_task_title,showInBottomBar = false
    );


    companion object {
        val bottomBarItems = listOf(
            Home,
            Favorite,
            Profile,
            Logout
        )

        fun getItem(route: String): Screen? {
            val findRoute = route.split("/").first()
            return values().find { value -> value.route.startsWith(findRoute) }
        }
    }
}