package com.pixeltodo.app.data.repository

import com.pixeltodo.app.data.local.TodoDao
import com.pixeltodo.app.data.local.TodoEntity
import com.pixeltodo.app.domain.model.Todo
import com.pixeltodo.app.domain.model.Priority
import com.pixeltodo.app.domain.model.RepeatType
import com.pixeltodo.app.domain.model.WeatherCondition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

fun TodoEntity.toDomain(): Todo = Todo(
    id = id,
    title = title,
    description = description,
    priority = Priority.valueOf(priority),
    isCompleted = isCompleted,
    dueDate = dueDate?.let { LocalDateTime.parse(it) },
    createdAt = createdAt.takeIf { it.isNotEmpty() }?.let { LocalDateTime.parse(it) } ?: LocalDateTime.now(),
    alarmTime = alarmTime?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) },
    isAlarmEnabled = isAlarmEnabled,
    repeatType = RepeatType.valueOf(repeatType),
    weatherCondition = weatherCondition?.let { WeatherCondition.valueOf(it) },
    weatherLocation = weatherLocation,
    isWeatherAlarmEnabled = isWeatherAlarmEnabled
)

fun Todo.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    description = description,
    priority = priority.name,
    isCompleted = isCompleted,
    dueDate = dueDate?.toString(),
    createdAt = createdAt.toString(),
    alarmTime = alarmTime?.toEpochSecond(java.time.ZoneOffset.UTC),
    isAlarmEnabled = isAlarmEnabled,
    repeatType = repeatType.name,
    weatherCondition = weatherCondition?.name,
    weatherLocation = weatherLocation,
    isWeatherAlarmEnabled = isWeatherAlarmEnabled
)

class TodoRepository(private val todoDao: TodoDao) {
    val allTodos: Flow<List<Todo>> = todoDao.getAllTodos().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun getTodoById(id: Long): Todo? {
        return todoDao.getTodoById(id)?.toDomain()
    }

    suspend fun insertTodo(todo: Todo): Long {
        return todoDao.insertTodo(todo.toEntity())
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.toEntity())
    }

    suspend fun deleteTodoById(id: Long) {
        todoDao.deleteTodoById(id)
    }

    suspend fun getTodosWithUpcomingAlarm(): List<Todo> {
        val now = java.time.LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC)
        return todoDao.getTodosWithUpcomingAlarm(now).map { it.toDomain() }
    }

    fun getTodosWithWeatherAlarm(): Flow<List<Todo>> {
        return todoDao.getTodosWithWeatherAlarm().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
