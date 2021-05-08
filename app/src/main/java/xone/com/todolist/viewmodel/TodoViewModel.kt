package xone.com.todolist.viewmodel

import android.app.Application
import android.graphics.Paint
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xone.com.todolist.R
import xone.com.todolist.database.entity.BottomSheetEntity
import xone.com.todolist.database.entity.BottomSheetItemEntity
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.repository.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    var todoRepository: TodoRepository? = null

    private val _todoSheetObservable = MutableLiveData<BottomSheetEntity>()
    val todoSheetObservable: LiveData<BottomSheetEntity> = _todoSheetObservable

    private val _dismissTodoSheetObservable = MutableLiveData<Unit>()
    val dismissTodoSheetObservable: LiveData<Unit> = _dismissTodoSheetObservable

    private val _editTodoEntity = MutableLiveData<Boolean>()
    val editTodoEntity: LiveData<Boolean> = _editTodoEntity

    private val _navigationObservable = MutableLiveData<TodoEntity>()
    val navigationObservable: LiveData<TodoEntity> = _navigationObservable

    init {
        todoRepository = TodoRepository(application)
    }

    fun allTodosBefor() = todoRepository?.allTodosBefor()!!

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            todoRepository?.updateTodo(todo)
        }
    }

    fun onTodoLongClick(todoEntity: TodoEntity) {
        _todoSheetObservable.value = BottomSheetEntity(
            items = listOf(
                BottomSheetItemEntity(
                    itemIconResource = R.drawable.ic_edit_icon_secondary_24dp,
                    itemTitleResource = R.string.todolist_todo_sheet_edit,
                    itemOnClickListener = { onEditTodoClick(todoEntity) }
                ),
                BottomSheetItemEntity(
                    itemIconResource = R.drawable.ic_delete_forever_icon_secondary_24dp,
                    itemTitleResource = R.string.todolist_todo_sheet_delete,
                    itemOnClickListener = { onDeleteTodoClick(todoEntity) }
                )
            ),
            sheetTitle = todoEntity.todo
        )
    }

    fun onClickListener(todoEntity: TodoEntity) {
        todoEntity.isDone = !todoEntity.isDone
        updateTodo(todoEntity)
    }

    fun onEditTodoClick(todoEntity: TodoEntity) {
        _editTodoEntity.value = true
        _dismissTodoSheetObservable.value = null
        _navigationObservable.value = todoEntity
    }

    private fun onDeleteTodoClick(todoEntity: TodoEntity) {
        _dismissTodoSheetObservable.value = null
        viewModelScope.launch {
            todoRepository?.deleteTodo(todoEntity)
        }
    }
}