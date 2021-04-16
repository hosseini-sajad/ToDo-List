package xone.com.todolist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xone.com.todolist.database.dao.TodoDao
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.database.TodoDatabase
import java.util.*

class TodoRepository(application: Application) {
    private var todoDao: TodoDao

    init {
        todoDao = TodoDatabase.getInstance(application).todoDao
    }

    fun allTodosBefor(): LiveData<List<TodoEntity>> =
        todoDao.selectAllTodosBefore()

    suspend fun insertTodo(todo: TodoEntity) = withContext(Dispatchers.IO) {
        return@withContext todoDao.insertToDo(todo)
    }
}