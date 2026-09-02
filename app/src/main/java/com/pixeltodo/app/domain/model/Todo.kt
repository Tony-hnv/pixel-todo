package com.pixeltodo.app.domain.model

import java.time.LocalDateTime

data class Todo(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val priority: Priority = Priority.MEDIUM,
    val isCompleted: Boolean = false,
    val dueDate: LocalDateTime? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val alarmTime: LocalDateTime? = null,
    val isAlarmEnabled: Boolean = false,
    val repeatType: RepeatType = RepeatType.NONE,
    val weatherCondition: WeatherCondition? = null,
    val weatherLocation: String = "",
    val isWeatherAlarmEnabled: Boolean = false
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class RepeatType {
    NONE, DAILY, WEEKLY, MONTHLY
}

enum class WeatherCondition {
    RAIN, SNOW, SUNNY, CLOUDY, WINDY
}
