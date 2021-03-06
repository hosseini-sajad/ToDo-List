package xone.com.todolist.database

import android.content.Context
import androidx.room.*
import xone.com.todolist.database.dao.TodoDao
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.database.typeconverter.DateTypeConverter

@Database(entities = [TodoEntity::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}