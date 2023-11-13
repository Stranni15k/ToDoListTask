package com.example.todolisttask.models.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "password")
    val password: String
) {

    @Ignore
    constructor(
        name: String,
        login: String,
        password: String
    ) : this(null, name, login, password)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        if (uid != other.uid) return false
        return true
    }

    override fun hashCode(): Int {
        return uid ?: -1
    }
}