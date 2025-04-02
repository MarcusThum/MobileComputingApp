package com.example.marcusfitnesstracker.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import com.example.marcusfitnesstracker.data.SleepScheduleEntity
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel.MealItem
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel.RecipeItem
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import kotlin.random.Random
import java.util.Calendar

object LocalVariables {

    // Fitness Statistics
    var todayTotalSteps: Int = 2000
    var targetSteps: Int = 0

    // Heart Rate and Sleep Data
    var heartRateData: List<Int> = generateHeartRateData()
    var sleepData: List<Float> = generateSleepData()

    // Average HeartRate and Last Sleep Hours
    var averageHeartRate: Int = heartRateData.average().toInt()
    @SuppressLint("DefaultLocale")
    var lastSleepTime: Double = String.format("%.2f", (sleepData.lastOrNull() ?: 0).toDouble()).toDouble()

    // Get Month and Day of Week
    var currentMonth = LocalDate.now().month
    var currentDay = LocalDate.now().dayOfMonth
    var calendar = Calendar.getInstance()
    @SuppressLint("ConstantLocale")
    var dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time)

    var selectedDay: Int = 0
    var selectedMeal: String = ""

    // Days Meals
    var meals = Array(7) { Array(3) { mutableListOf<MealItem>() } }

    data class Recipe(
        val name: String,
        val servings: Int,
        val likes: Int,
        val imageUrl: String
    )

    data class RecipeResponse(
        val recipes: List<Recipe>
    )

    var recipeList = mutableListOf<RecipeItem>()

    var height: Float = 0f
    var weight: Float = 0f
    var age: Float = 0f
    var gender: String = ""

}

fun generateHeartRateData(): List<Int> {
    return List(20) { Random.nextInt(60, 120) }
}

//fun generateSleepData(): List<Float> {
//    return List(20) { Random.nextFloat() * 8f }
//}

fun generateSleepData(): List<Float> {
    val mean = 6f // Average sleep duration
    val variance = 1.5f // Variation in sleep data

    return List(7) { mean + (Random.nextFloat() * variance * 2 - variance) }  // Random data centered around mean
}

