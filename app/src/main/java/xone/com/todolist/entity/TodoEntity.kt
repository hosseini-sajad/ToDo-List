package xone.com.todolist.entity

import java.io.Serializable
import java.util.Date

data class TodoEntity(
        val todoId: Long,
        val todo: String,
        val date: Date,
        val isDone: Boolean = false
) : Serializable