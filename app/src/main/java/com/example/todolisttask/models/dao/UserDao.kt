package com.example.todolisttask.models.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolisttask.models.model.TaskWithUser
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from users order by name collate nocase asc")
    fun getAll(): Flow<List<User>>

    @Query("select * from users left join tasks on users.uid = tasks.user_id where users.uid = :uid")
    suspend fun getByUid(uid: Int): TaskWithUser

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}