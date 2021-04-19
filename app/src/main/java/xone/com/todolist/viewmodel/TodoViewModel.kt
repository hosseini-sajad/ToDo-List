package xone.com.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.repository.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    var todoRepository: TodoRepository? = null

    init {
        todoRepository = TodoRepository(application)

    }

    fun allTodosBefor() = todoRepository?.allTodosBefor()!!

    fun insertTodo(todo: TodoEntity): LiveData<Long> {
        val id = MutableLiveData<Long>()
        viewModelScope.launch {
            id.value = todoRepository?.insertTodo(todo)
        }
        return id
    }
}