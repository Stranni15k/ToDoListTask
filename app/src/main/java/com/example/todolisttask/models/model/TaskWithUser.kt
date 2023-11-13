package com.example.todolisttask.models.model

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class TaskWithUser(
    @Embedded
    val user: User,
    @ColumnInfo(name = "task_name")
    val taskName: String
)