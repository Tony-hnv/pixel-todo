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
