package com.example.todolisttask

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolisttask.models.dao.TaskDao
import com.example.todolisttask.models.dao.UserDao
import com.example.todolisttask.models.model.Task
import com.example.todolisttask.models.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DB_NAME: String = "pmy-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private suspend fun populateDatabase() {
            INSTANCE?.let { database ->
                // Groups
//                val taskDao = database.taskDao()
//                val task1 = Task(1, "Test1", "TestDesk",1)
//                val task2 = Task(2, "Test2", "TestDesk",1)
//                val task3 = Task(3, "Test3", "TestDesk",1)
//                taskDao.insert(task1)
//                taskDao.insert(task2)
//                taskDao.insert(task3)
                // Students
                val userDao = database.userDao()
                val user1 = User("Sergey", "User", "123456")
                userDao.insert(user1)
            }
        }

        fun getInstance(appContext: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase()
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}