package com.example.todolisttask.models.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "tasks", foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["uid"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.RESTRICT,
        onUpdate = ForeignKey.RESTRICT
    )
])
data class Task(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,
    @ColumnInfo(name = "task_name")
    val name: String,
    @ColumnInfo(name = "task_description")
    val description: String,
    @ColumnInfo(name = "user_id", index = true)
    val userId: Int
) {
    @Ignore
    constructor(
        name: String,
        description: String,
        userId: Int
    ) : this(null, name, description, userId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Task
        if (uid != other.uid) return false
        return true
    }

    override fun hashCode(): Int {
        return uid ?: -1
    }
}