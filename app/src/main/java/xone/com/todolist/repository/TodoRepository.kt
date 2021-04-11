package xone.com.todolist.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.database.typeconverter.TodoDatabase
import java.util.*

class TodoRepository(private val database: TodoDatabase, private val date: Date) {
    val todos: LiveData<List<TodoEntity>> =
        database.todoDao.selectAllTodosBefore(date)

    suspend fun insertTodo(todo: TodoEntity) = withContext(Dispatchers.IO) {
        database.todoDao.insertToDo(todo)
    }
}