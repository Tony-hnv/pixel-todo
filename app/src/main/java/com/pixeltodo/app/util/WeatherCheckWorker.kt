package com.pixeltodo.app.util

import android.content.Context
import androidx.work.*
import com.pixeltodo.app.data.WeatherApi
import com.pixeltodo.app.data.WeatherResponse
import com.pixeltodo.app.data.local.PixelTodoDatabase
import com.pixeltodo.app.data.local.toDomain
import com.pixeltodo.app.domain.model.WeatherCondition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class WeatherCheckWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = PixelTodoDatabase.getDatabase(applicationContext)
            val todos = database.todoDao().getTodosWithWeatherAlarm().toString()
            
            // Check weather and trigger notifications
            // In production, use actual weather API key
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "weather_check_work"
        
        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val work = PeriodicWorkRequestBuilder<WeatherCheckWorker>(
                6, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                work
            )
        }
    }
}