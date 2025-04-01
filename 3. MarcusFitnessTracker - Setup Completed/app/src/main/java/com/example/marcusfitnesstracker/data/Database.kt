package com.example.marcusfitnesstracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserSetup::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSetupDao(): UserSetupDao
}
