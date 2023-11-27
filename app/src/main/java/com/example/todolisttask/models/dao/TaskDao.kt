package com.example.todolisttask.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolisttask.models.model.Task
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("select * from tasks order by task_name collate nocase asc")
     fun getAll(): Flow<List<Task>>

    @Insert
    suspend fun insert(group: Task)

    @Update
    suspend fun update(group: Task)

    @Delete
    suspend fun delete(group: Task)

    @Query("select * from tasks where tasks.user_id = :uid")
    suspend fun getByUid(uid: Int): List<Task>
    @Query("select * from tasks where tasks.uid = :uid ")
    suspend fun getOne(uid: Int): Task
}