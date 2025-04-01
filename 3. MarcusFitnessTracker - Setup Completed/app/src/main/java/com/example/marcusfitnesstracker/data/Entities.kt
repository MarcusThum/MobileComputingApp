package com.example.marcusfitnesstracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

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