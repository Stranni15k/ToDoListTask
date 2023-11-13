package com.example.todolisttask.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolisttask.models.model.Task

@Dao
interface TaskDao {
    @Query("select * from tasks order by task_name collate nocase asc")
    suspend fun getAll(): List<Task>

    @Insert
    suspend fun insert(group: Task)

    @Update
    suspend fun update(group: Task)

    @Delete
    suspend fun delete(group: Task)
}