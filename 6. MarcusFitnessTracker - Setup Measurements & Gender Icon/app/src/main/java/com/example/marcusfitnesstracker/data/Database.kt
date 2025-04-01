package com.example.marcusfitnesstracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserSetup::class, MealEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSetupDao(): UserSetupDao
    abstract fun mealDao(): MealDao
}
