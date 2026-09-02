package com.pixeltodo.app.data.repository

import com.pixeltodo.app.data.local.TodoDao
import com.pixeltodo.app.data.local.toDomain
import com.pixeltodo.app.data.local.toEntity
import com.pixeltodo.app.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos().map { list ->
        list.map { it.toDomain() }
    }

    fun getTodosWithWeatherAlarm(): Flow<List<Todo>> = todoDao.getTodosWithWeatherAlarm().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun getTodoById(id: Long): Todo? = todoDao.getTodoById(id)?.toDomain()

    suspend fun insertTodo(todo: Todo): Long = todoDao.insertTodo(todo.toEntity())

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo.toEntity())

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo.toEntity())

    suspend fun deleteTodoById(id: Long) = todoDao.deleteTodoById(id)

    suspend fun getTodosWithUpcomingAlarm(time: String): List<Todo> = 
        todoDao.getTodosWithUpcomingAlarm(java.time.LocalDateTime.parse(time)).map { it.toDomain() }
}