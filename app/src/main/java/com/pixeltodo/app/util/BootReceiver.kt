package com.pixeltodo.app.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pixeltodo.app.data.local.PixelTodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Reschedule alarms after boot
            CoroutineScope(Dispatchers.IO).launch {
                val database = PixelTodoDatabase.getDatabase(context)
                // Re-register alarms from database
            }
            // Reschedule weather check
            WeatherCheckWorker.schedule(context)
        }
    }
}