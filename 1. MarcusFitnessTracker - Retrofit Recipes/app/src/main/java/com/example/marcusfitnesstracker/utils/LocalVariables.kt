package com.example.marcusfitnesstracker.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.TextStyle
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel.MealItem
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel.RecipeItem
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import kotlin.random.Random
import java.util.Calendar

object LocalVariables {

    // Fitness Statistics
    var todayTotalSteps: Int = 2000
    var targetSteps: Int = 10000
    var distance: Double = 5.6
    var caloriesBurned: Double = 150.0

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

}

fun generateHeartRateData(): List<Int> {
    return List(20) { Random.nextInt(60, 120) }
}

fun generateSleepData(): List<Float> {
    return List(20) { Random.nextFloat() * 8f }
}