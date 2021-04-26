package xone.com.todolist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import xone.com.todolist.database.TodoDatabase
import xone.com.todolist.database.dao.TodoDao
import xone.com.todolist.database.entity.TodoEntity

class TodoRepository(application: Application) {
    private var todoDao: TodoDao

    init {
        todoDao = TodoDatabase.getInstance(application).todoDao
    }

    fun allTodosBefor(): LiveData<List<TodoEntity>> =
        todoDao.selectAllTodosBefore()

    suspend fun insertTodo(todo: TodoEntity) =
        todoDao.insertToDo(todo)

    suspend fun updateTodo(todo: TodoEntity) =
        todoDao.updateTodo(todo)
}