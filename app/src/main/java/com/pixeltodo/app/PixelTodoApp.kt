package com.pixeltodo.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class PixelTodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "pixel_alarm",
                "闹钟提醒",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "像素待办闹钟提醒"
                enableVibration(true)
            }
            val channel2 = NotificationChannel(
                "pixel_weather",
                "天气提醒",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "天气触发提醒"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
            manager.createNotificationChannel(channel2)
        }
    }
}