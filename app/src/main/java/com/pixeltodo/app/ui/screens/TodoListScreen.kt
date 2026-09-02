package com.pixeltodo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pixeltodo.app.ui.components.PixelButton
import com.pixeltodo.app.ui.components.PixelTextField
import com.pixeltodo.app.ui.components.TodoItem
import com.pixeltodo.app.ui.theme.*
import com.pixeltodo.app.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoViewModel = viewModel(),
    onAddTodo: () -> Unit,
    onEditTodo: (Long) -> Unit
) {
    val todos by viewModel.todos.collectAsState()
    var showCompleted by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("📝 Pixel Todo", fontFamily = FontFamily.Monospace)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PixelBackground,
                    titleContentColor = PixelText
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTodo,
                containerColor = PixelBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = PixelBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Filter toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "待办事项 (${todos.count { !it.isCompleted }})",
                    color = PixelText,
                    fontFamily = FontFamily.Monospace
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("显示已完成", color = PixelGray, fontFamily = FontFamily.Monospace)
                    Switch(
                        checked = showCompleted,
                        onCheckedChange = { showCompleted = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = PixelGreen,
                            checkedTrackColor = PixelCardBackground
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredTodos = if (showCompleted) todos else todos.filter { !it.isCompleted }

            if (filteredTodos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎮", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "没有待办事项",
                            color = PixelGray,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            "点击 + 添加一个新任务",
                            color = PixelGray,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredTodos, key = { it.id }) { todo ->
                        TodoItem(
                            todo = todo,
                            onToggleComplete = { viewModel.toggleComplete(todo) },
                            onClick = { onEditTodo(todo.id) }
                        )
                    }
                }
            }
        }
    }
}