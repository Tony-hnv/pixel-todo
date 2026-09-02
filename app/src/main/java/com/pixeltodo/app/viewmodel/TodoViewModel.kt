package com.pixeltodo.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pixeltodo.app.data.local.PixelTodoDatabase
import com.pixeltodo.app.data.repository.TodoRepository
import com.pixeltodo.app.domain.model.Todo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository

    val todos: StateFlow<List<Todo>>

    init {
        val database = PixelTodoDatabase.getDatabase(application)
        repository = TodoRepository(database.todoDao())
        todos = repository.allTodos
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            repository.deleteTodoById(id)
        }
    }

    fun toggleComplete(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    suspend fun getTodoById(id: Long): Todo? {
        return repository.getTodoById(id)
    }
}