package com.pixeltodo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pixeltodo.app.domain.model.Todo
import com.pixeltodo.app.ui.screens.EditTodoScreen
import com.pixeltodo.app.ui.screens.TodoListScreen
import com.pixeltodo.app.ui.theme.PixelBackground
import com.pixeltodo.app.ui.theme.PixelTodoTheme
import com.pixeltodo.app.util.WeatherCheckWorker
import com.pixeltodo.app.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Schedule weather check worker
        WeatherCheckWorker.schedule(this)

        setContent {
            PixelTodoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PixelBackground
                ) {
                    PixelTodoNavHost()
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Edit : Screen("edit/{todoId}") {
        fun createRoute(todoId: Long) = "edit/$todoId"
        const val ARG = "todoId"
    }
    object Add : Screen("add")
}

@Composable
fun PixelTodoNavHost() {
    val navController = rememberNavController()
    val viewModel: TodoViewModel = viewModel()

    var editingTodo by remember { mutableStateOf<Long?>(null) }

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(Screen.List.route) {
            TodoListScreen(
                viewModel = viewModel,
                onAddTodo = { navController.navigate(Screen.Add.route) },
                onEditTodo = { id ->
                    editingTodo = id
                    navController.navigate(Screen.Edit.createRoute(id))
                }
            )
        }

        composable(Screen.Add.route) {
            EditTodoScreen(
                todo = null,
                onSave = { todo ->
                    viewModel.addTodo(todo)
                    navController.popBackStack()
                },
                onDelete = { },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Edit.route,
            arguments = listOf(
                navArgument(Screen.Edit.ARG) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong(Screen.Edit.ARG) ?: 0L
            var todo by remember { mutableStateOf<Todo?>(null) }

            LaunchedEffect(todoId) {
                todo = viewModel.getTodoById(todoId)
            }

            todo?.let { t ->
                EditTodoScreen(
                    todo = t,
                    onSave = { updatedTodo ->
                        viewModel.updateTodo(updatedTodo)
                        navController.popBackStack()
                    },
                    onDelete = { id ->
                        viewModel.deleteTodo(id)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
