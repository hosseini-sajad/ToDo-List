package xone.com.todolist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "todo_table")
internal data class TodoEntity(
        @PrimaryKey(autoGenerate = true)
        val todoId: Long = 0L,
        val todo: String,
        val date: Date,
        val isDone: Boolean = false
) : Serializable