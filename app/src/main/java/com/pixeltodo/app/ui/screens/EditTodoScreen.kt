package com.pixeltodo.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.pixeltodo.app.domain.model.Priority
import com.pixeltodo.app.domain.model.RepeatType
import com.pixeltodo.app.domain.model.Todo
import com.pixeltodo.app.domain.model.WeatherCondition
import com.pixeltodo.app.ui.components.PixelButton
import com.pixeltodo.app.ui.components.PixelTextField
import com.pixeltodo.app.ui.theme.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(
    todo: Todo?,
    onSave: (Todo) -> Unit,
    onDelete: (Long) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(todo?.title ?: "") }
    var description by remember { mutableStateOf(todo?.description ?: "") }
    var priority by remember { mutableStateOf(todo?.priority ?: Priority.MEDIUM) }
    var isAlarmEnabled by remember { mutableStateOf(todo?.isAlarmEnabled ?: false) }
    var isWeatherAlarmEnabled by remember { mutableStateOf(todo?.isWeatherAlarmEnabled ?: false) }
    var weatherCondition by remember { mutableStateOf(todo?.weatherCondition ?: WeatherCondition.RAINY) }
    var weatherLocation by remember { mutableStateOf(todo?.weatherLocation ?: "") }
    var repeatType by remember { mutableStateOf(todo?.repeatType ?: RepeatType.NONE) }

    val isEditing = todo != null
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditing) "✏️ 编辑任务" else "➕ 新建任务",
                        fontFamily = FontFamily.Monospace
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PixelText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PixelBackground,
                    titleContentColor = PixelText
                )
            )
        },
        containerColor = PixelBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title
            PixelTextField(
                value = title,
                onValueChange = { title = it },
                label = "任务标题",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            PixelTextField(
                value = description,
                onValueChange = { description = it },
                label = "任务描述",
                modifier = Modifier.fillMaxWidth(),
                singleLine = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Priority
            Text("优先级", color = PixelText, fontFamily = FontFamily.Monospace)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Priority.values().forEach { p ->
                    FilterChip(
                        selected = priority == p,
                        onClick = { priority = p },
                        label = {
                            Text(
                                when (p) {
                                    Priority.HIGH -> "🔴 高"
                                    Priority.MEDIUM -> "🟡 中"
                                    Priority.LOW -> "🟢 低"
                                },
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = when (p) {
                                Priority.HIGH -> PixelRed
                                Priority.MEDIUM -> PixelYellow
                                Priority.LOW -> PixelGreen
                            }.copy(alpha = 0.3f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Alarm Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PixelCardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("⏰ 闹钟提醒", color = PixelText, fontFamily = FontFamily.Monospace)
                        Switch(
                            checked = isAlarmEnabled,
                            onCheckedChange = { isAlarmEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PixelYellow,
                                checkedTrackColor = PixelCardBackground
                            )
                        )
                    }

                    if (isAlarmEnabled) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("重复", color = PixelGray, fontFamily = FontFamily.Monospace)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RepeatType.values().forEach { r ->
                                FilterChip(
                                    selected = repeatType == r,
                                    onClick = { repeatType = r },
                                    label = {
                                        Text(
                                            when (r) {
                                                RepeatType.NONE -> "不重复"
                                                RepeatType.DAILY -> "每天"
                                                RepeatType.WEEKDAYS -> "工作日"
                                                RepeatType.WEEKLY -> "每周"
                                            },
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 10.sp
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weather Alarm Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PixelCardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("🌤️ 天气提醒", color = PixelText, fontFamily = FontFamily.Monospace)
                        Switch(
                            checked = isWeatherAlarmEnabled,
                            onCheckedChange = { isWeatherAlarmEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PixelBlue,
                                checkedTrackColor = PixelCardBackground
                            )
                        )
                    }

                    if (isWeatherAlarmEnabled) {
                        Spacer(modifier = Modifier.height(8.dp))
                        PixelTextField(
                            value = weatherLocation,
                            onValueChange = { weatherLocation = it },
                            label = "城市（如：beijing）",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("天气条件", color = PixelGray, fontFamily = FontFamily.Monospace)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            WeatherCondition.values().forEach { w ->
                                FilterChip(
                                    selected = weatherCondition == w,
                                    onClick = { weatherCondition = w },
                                    label = {
                                        Text(
                                            when (w) {
                                                WeatherCondition.SUNNY -> "☀️"
                                                WeatherCondition.CLOUDY -> "☁️"
                                                WeatherCondition.RAINY -> "🌧️"
                                                WeatherCondition.SNOWY -> "❄️"
                                                WeatherCondition.THUNDERSTORM -> "⛈️"
                                            },
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isEditing) {
                    PixelButton(
                        text = "🗑️ 删除",
                        onClick = { todo?.id?.let { onDelete(it) } },
                        color = PixelRed,
                        modifier = Modifier.weight(1f)
                    )
                }

                PixelButton(
                    text = if (isEditing) "💾 保存" else "➕ 创建",
                    onClick = {
                        val newTodo = Todo(
                            id = todo?.id ?: 0,
                            title = title,
                            description = description,
                            priority = priority,
                            isCompleted = todo?.isCompleted ?: false,
                            dueDate = todo?.dueDate,
                            createdAt = todo?.createdAt ?: LocalDateTime.now(),
                            alarmTime = todo?.alarmTime,
                            isAlarmEnabled = isAlarmEnabled,
                            repeatType = repeatType,
                            weatherCondition = if (isWeatherAlarmEnabled) weatherCondition else null,
                            weatherLocation = weatherLocation,
                            isWeatherAlarmEnabled = isWeatherAlarmEnabled
                        )
                        onSave(newTodo)
                    },
                    enabled = title.isNotBlank(),
                    color = PixelGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}