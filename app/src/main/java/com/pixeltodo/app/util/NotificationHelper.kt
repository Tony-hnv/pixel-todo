package com.pixeltodo.app.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.pixeltodo.app.MainActivity
import com.pixeltodo.app.R

object NotificationHelper {
    private const val CHANNEL_ALARM = "pixel_alarm"
    private const val CHANNEL_WEATHER = "pixel_weather"

    fun showAlarmNotification(context: Context, title: String, todoId: Long) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("todoId", todoId)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            todoId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ALARM)
            .setSmallIcon(R.drawable.ic_pixel_alarm)
            .setContentTitle("⏰ $title")
            .setContentText("点击查看待办事项")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(todoId.toInt(), notification)
    }

    fun showWeatherNotification(context: Context, title: String, weather: String, todoId: Long) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("todoId", todoId)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            (todoId + 10000).toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_WEATHER)
            .setSmallIcon(R.drawable.ic_pixel_weather)
            .setContentTitle("🌤️ $title")
            .setContentText(weather)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify((todoId + 10000).toInt(), notification)
    }
}