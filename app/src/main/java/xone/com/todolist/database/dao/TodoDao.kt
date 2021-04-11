package xone.com.todolist.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import xone.com.todolist.database.entity.TodoEntity
import java.util.*

@Dao
interface TodoDao {

    @Insert
    suspend fun insertToDo(todo: TodoEntity): Long

    @Update
    suspend fun updateTodo(todo: TodoEntity): Int

    @Delete
    suspend fun deleteTodo(todo: TodoEntity): Int

    @Delete
    suspend fun deleteTodos(todos: List<TodoEntity>): Int

    @Query("SELECT * FROM todo_table WHERE date < :before ORDER BY isDone ASC, todoId DESC")
    fun selectAllTodosBefore(before: Date): LiveData<List<TodoEntity>>

    @Query("SELECT * FROM todo_table WHERE date < :before AND isDone = 1")
    suspend fun selectAllDoneTodosBefore(before: Date): List<TodoEntity>
}