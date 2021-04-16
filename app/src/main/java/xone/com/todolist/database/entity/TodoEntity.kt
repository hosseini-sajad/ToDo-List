package xone.com.todolist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "todo_table")
data class TodoEntity(
        val todo: String,
        val date: Date,
        val isDone: Boolean = false
) : Serializable {
        @PrimaryKey(autoGenerate = true)
        var todoId: Long = 0
}