package xone.com.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.repository.TodoRepository
import java.util.*

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    val allTodosBefor: LiveData<List<TodoEntity>>

    var todoRepository: TodoRepository? = null

    init {
        todoRepository = TodoRepository(application)
        allTodosBefor = todoRepository?.allTodosBefor()!!
    }

    fun insertTodo(todo: TodoEntity): LiveData<Long> {
        val id = MutableLiveData<Long>()
        viewModelScope.launch {
            id.value = todoRepository?.insertTodo(todo)
        }
        return id
    }

    class Factory(
            private val application: Application
//            private val date: Date,
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                return TodoViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}