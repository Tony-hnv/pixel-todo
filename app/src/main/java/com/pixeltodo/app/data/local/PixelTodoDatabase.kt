package com.pixeltodo.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class PixelTodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: PixelTodoDatabase? = null

        fun getDatabase(context: Context): PixelTodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PixelTodoDatabase::class.java,
                    "pixel_todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}