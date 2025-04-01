package com.example.marcusfitnesstracker


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcusfitnesstracker.viewmodel.AppBarViewModel
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel
import com.example.marcusfitnesstracker.viewmodel.SetupScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val fitnessViewModel: FitnessViewModel by viewModel()
    private val mealPlanningViewModel: MealPlanningViewModel by viewModel()
    private val appBarViewModel: AppBarViewModel by viewModel()
    private val sleepPatternViewModel: AppBarViewModel by viewModel()
    private val setupScreenViewModel: SetupScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appBarViewModel.AppBar()
        }
    }

}