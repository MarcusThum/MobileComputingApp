package com.example.marcusfitnesstracker


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.example.marcusfitnesstracker.viewmodel.AppBarViewModel
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val fitnessViewModel: FitnessViewModel by viewModel()
    private val mealPlanningViewModel: MealPlanningViewModel by viewModel()
    private val appBarViewModel: AppBarViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appBarViewModel.AppBar()
        }
    }

}