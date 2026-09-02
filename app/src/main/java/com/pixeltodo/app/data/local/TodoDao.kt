package com.pixeltodo.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY isCompleted ASC, priority DESC, dueDate ASC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Long): TodoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodoById(id: Long)

    @Query("SELECT * FROM todos WHERE isAlarmEnabled = 1 AND alarmTime > :time")
    suspend fun getTodosWithUpcomingAlarm(time: Long): List<TodoEntity>

    @Query("SELECT * FROM todos WHERE isWeatherAlarmEnabled = 1")
    fun getTodosWithWeatherAlarm(): Flow<List<TodoEntity>>
}

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val priority: String = "MEDIUM",
    val isCompleted: Boolean = false,
    val dueDate: String? = null,
    val createdAt: String = "",
    val alarmTime: Long? = null,
    val isAlarmEnabled: Boolean = false,
    val repeatType: String = "NONE",
    val weatherCondition: String? = null,
    val weatherLocation: String = "",
    val isWeatherAlarmEnabled: Boolean = false
)
