package com.example.marcusfitnesstracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel

@Entity(tableName = "sleep_schedule")
data class SleepScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bedTime: String,
    val wakeUpTime: String
)

@Entity(tableName = "meal_items")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val servings: String,
    val day: Int,         // 0-6
    val mealIndex: Int,   // 0-2
    val imageUrl: String  // Link or file path to the image
)

@Entity(tableName = "user_setup")
data class UserSetup(
    @PrimaryKey val id: Int = 0,
    val targetSteps: Float,
    val height: Float,
    val weight: Float,
    val age: Float,
    val gender: String,
    val setupCompleted: Boolean
)